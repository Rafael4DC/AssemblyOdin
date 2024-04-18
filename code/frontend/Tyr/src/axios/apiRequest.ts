import axios, {AxiosError, AxiosRequestConfig} from 'axios';

// Base Axios instance
const axiosInstance = axios.create({
    baseURL: 'https://api.yourservice.com'
});

/**
 * Handles errors by logging and re-throwing them.
 * @param {Error} error - The error caught from Axios.
 */
function handleError(error: AxiosError) {
    console.error('API Request Failed:', error);
    // Detailed error handling based on error type
    if (error.response) {
        // The server responded with a status code outside the 2xx range
        throw new Error(`API Error: ${error.response.status} ${error.response.statusText}`);
    } else if (error.request) {
        // The request was made but no response was received
        throw new Error('No response received');
    } else {
        // An error occurred in setting up the request
        throw new Error(error.message);
    }
}

/**
 * Wrapper function for making API calls.
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

// Usage example
makeApiRequest('get', '/someEndpoint')
    .then(data => console.log('Data received:', data))
    .catch(err => console.error('Error occurred:', err.message));
