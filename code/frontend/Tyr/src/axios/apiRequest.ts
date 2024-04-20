import axios, {AxiosError, AxiosRequestConfig} from 'axios';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080/'
});

/**
 * Handles errors by logging and re-throwing them.
 *
 * @param {Error} error - The error caught from Axios.
 */
function handleError(error: AxiosError) {
    console.error('API Request Failed:', error);
    if (error.response) {
        throw new Error(`API Error: ${error.response.status} ${error.response.statusText}`);
    } else if (error.request) {
        throw new Error('No response received');
    } else {
        throw new Error(error.message);
    }
}

/**
 * Wrapper function for making API calls using axios.
 *
 * @param {string} method - The HTTP method to use.
 * @param {string} url - The URL endpoint.
 * @param {any} [data] - The payload for POST/PUT requests.
 * @param {AxiosRequestConfig} [config] - Additional Axios config.
 */
async function makeApiRequest(method: string, url: string, data?: any, config?: AxiosRequestConfig) {
    try {
        const response = await axiosInstance({
            method,
            url,
            data,
            ...config
        });
        return response.data;
    } catch (error) {
        return handleError(error);
    }
}

export { makeApiRequest };
