import {Either, failure, success} from "../services/_utils/Either";
import {Problem, problemMediaType} from "../services/_utils/Problem";

/**
 * Makes an API request.
 *
 * @param method the HTTP method
 * @param url the URL
 * @param data the data to send
 *
 * @return the API response
 */
async function apiRequest<T>(method: string, url: string, data?: any): Promise<FetchRespose<T>> {
    const [err, res] = await toResOrError<Response>(fetch(`/api${url}`,
        {
            method: method,
            headers: {
                'Content-Type': jsonMediaType
            },
            credentials: 'include',
            redirect: 'follow',
            body: data ? JSON.stringify(data) : undefined
        }));

    if (err) {
        console.error('API Request Failed:', err);
        throw new NetworkError(err.message);
    }

    const contentType = res?.headers.get('Content-Type');

    if (!res.ok) {
        if (contentType !== problemMediaType)
            return failure(new UnexpectedResponseError(`Unexpected response type: ${contentType}`));
        else {
            console.log("API fetch Problem+JSON:", res)
            return failure(new Problem(await res.json()));
        }
    } else {
        if (res.redirected) {
            window.location.href = res.url.replace('1337', '8080');
            return;
        }

        if (contentType !== jsonMediaType)
            return failure(new UnexpectedResponseError(`Unexpected response type: ${contentType}`));
        else {
            console.log("API fetch JSON:", res)
            return success(await res.json());
        }
    }
}

/**
 * Converts a promise to a result or an error.
 *
 * @param promise the promise
 *
 * @return the result or error
 */
async function toResOrError<T, E = Error>(promise: Promise<T>): Promise<[E, null] | [null, T]> {
    try {
        const data = await promise;
        return [null, data];
    } catch (err) {
        return [err, null];
    }
}

export const jsonMediaType = "application/json"

/**
 * The response of an API fetch.
 */
type FetchRespose<T> = Either<Error | Problem, T>;

/**
 * Network error.
 *
 * @param message the error message
 */
export class NetworkError extends Error {
    constructor(message: string) {
        super(message)
    }
}

/**
 * Unexpected response error.
 *
 * @param message the error message
 */
export class UnexpectedResponseError extends Error {
    constructor(message: string) {
        super(message)
    }
}

export {apiRequest};
