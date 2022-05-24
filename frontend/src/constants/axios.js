import axios from "axios";
import { BASE_API_URL } from "./requests";


const axiosInstance = axios.create({
    baseURL: BASE_API_URL,
    timeout: 5000,
    headers: {
        "Authorization": window.localStorage.getItem("accessToken")
            ? 'Bearer ' + window.localStorage.getItem("accessToken")
            : null,
        "Content-Type": 'application/json',
        "accept": 'application/json',
    }
});

axiosInstance.interceptors.response.use(
    response => response,
    (error) => {
        console.log(error);
        const originalRequest = error.config;

        if (typeof error.response === "undefined") {
            alert(
                'A server/network error occurred. ' +
                'Looks like CORS might be the problem. ' +
                'Sorry about this - we will get it fixed shortly.'
            )
            return Promise.reject(error);
        }

        if (
            error.response.status === 401 &&
            originalRequest.url === '/api/auth/token'
        ) {
            window.location.href = '/';
        }

        if (
            error.response.status === 401 ||
            error.response.status === 403
        ) {
            const refreshToken = window.localStorage.getItem('refreshToken');

            if (refreshToken) {
                const tokenParts = JSON.parse(atob(refreshToken.split('.')[1]));
                const now = Math.ceil(Date.now() / 1000);

                if (tokenParts.exp > now) {
                    return axiosInstance
                        .post('/auth/token', { refreshToken: refreshToken })
                        .then(res => {
                            window.localStorage.setItem('refreshToken', res.data.refreshToken);
                            window.localStorage.setItem('accessToken', res.data.accessToken);

                            axiosInstance.defaults.headers['Authorization'] = 
                                res.data.tokenType + " " + res.data.accessToken;
                            originalRequest.headers['Authorization'] = 
                                res.data.tokenType + " " + res.data.accessToken;
                        })
                        .catch(err => removeAuthorizationData());
                } else {
                    console.log('Refresh token is expired', tokenParts.exp, now);
                    removeAuthorizationData();
                }
            } else {
                console.log('Refresh token not available.');
                removeAuthorizationData();
            }
        }

        return Promise.reject(error);
    }
)


export const removeAuthorizationData = () => {
    axiosInstance.defaults.headers['Authorization'] = null;
    ['accessToken', 'refreshToken'].forEach(key => window.localStorage.removeItem(key));
    
    window.location.href = '/'
}

export const addAuthorizationData = (data) => {
    ['accessToken', 'refreshToken'].forEach(key =>
        window.localStorage.setItem(key, data[key]));

    axiosInstance.defaults.headers['Authorization'] = 
        (data.tokenType || 'Bearer') + " " + data.accessToken;

    window.location.href = '/';
}


export default axiosInstance;