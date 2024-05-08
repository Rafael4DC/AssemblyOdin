
/**
 * Enhanced wrapper function for making API calls using Fetch API.
 *
 * @param {string} method - The HTTP method to use (GET, POST, PUT, DELETE, etc.).
 * @param {string} url - The URL endpoint.
 * @param {any} [data] - The payload for POST/PUT requests.
 */
async function makeApiRequest(method: string, url: string, data?: any) {
    const headers = {
        'Content-Type': 'application/json'
    };

    try {
        const response = await fetch(`/api${url}`,
            {
                method: method,
                headers: headers,
                credentials: 'include',
                redirect: 'follow',
                body: data ? JSON.stringify(data) : undefined
            });

        if (!response.ok) {
            const errorBody = await response.json();
            throw new Error(`HTTP error ${response.status}: ${errorBody}`);
        }

        if (response.redirected) {
            window.location.href = response.url.replace('1337', '8080');
            return;
        }

        return await response.json();
    } catch (error) {
        console.error('API Request Failed:', error);
        throw error;
    }
}

export {makeApiRequest};
