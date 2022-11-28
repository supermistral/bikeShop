import axios, { AxiosError } from "axios";
import { BASE_API_URL } from "./requests";
import { JWTData, JWTokensData } from "./types";


type ErrorResponseData = {
    message: string;
}


const axiosInstance = axios.create({
    baseURL: BASE_API_URL,
    timeout: 5000,
});

axiosInstance.defaults.headers.common = {
    ...axiosInstance.defaults.headers.common,
    "Authorization": window.localStorage.getItem("accessToken")
        ? 'Bearer ' + window.localStorage.getItem("accessToken")
        : false,
    "Content-Type": 'application/json',
    "Accept": 'application/json',
};

axiosInstance.interceptors.response.use(
    response => response,
    async (error: AxiosError<ErrorResponseData, any>) => {
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

        const statusCode = error.response.status;
        const message = error.response.data.message.toLowerCase();

        if (
            statusCode === 401 &&
            message.includes("refresh token was expired")
        ) {
            console.log('Refresh token is expired');
            window.location.href = '/logout';
        }

        if (
            statusCode === 401 &&
            message.includes("is not in database")
        ) {
            removeAuthorizationData('/login');
        }

        // if (
        //     error.response.status === 401 &&
        //     originalRequest.url === '/api/auth/token'
        // ) {
        //     window.location.href = '/';
        // }

        console.log(message, statusCode);

        if (
            statusCode === 401 &&
            message.includes("token was expired")
        ) {
            const refreshToken = window.localStorage.getItem('refreshToken');

            if (refreshToken) {
                return axiosInstance
                    .post('/auth/token', { refreshToken: refreshToken })
                    .then(res => {
                        window.localStorage.setItem('refreshToken', res.data.refreshToken);
                        window.localStorage.setItem('accessToken', res.data.accessToken);

                        axiosInstance.defaults.headers.common['Authorization'] = 
                            res.data.tokenType + " " + res.data.accessToken;
                        originalRequest.headers!['Authorization'] = 
                            res.data.tokenType + " " + res.data.accessToken;

                        window.location.href = window.location.href;
                    })
                    .catch(err => {
                        console.log(err)
                        removeAuthorizationData();
                    });
            } else {
                console.log('Refresh token is not available');
                removeAuthorizationData();
            }
        }

        return Promise.reject(error);
    }
)


export const removeAuthorizationData = (path = "/") => {
    axiosInstance.defaults.headers.common['Authorization'] = false;
    ['accessToken', 'refreshToken'].forEach(key => window.localStorage.removeItem(key));
    
    window.location.href = path;
}

export const addAuthorizationData = (data: JWTData) => {
    ['accessToken', 'refreshToken'].forEach(key =>
        window.localStorage.setItem(key, data[key as keyof JWTokensData]));

    axiosInstance.defaults.headers.common['Authorization'] = 
        (data.tokenType || 'Bearer') + " " + data.accessToken;

    window.location.href = '/';
}


export default axiosInstance;