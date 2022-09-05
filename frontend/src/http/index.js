import axios from "axios";

const API_URL = `${process.env.REACT_APP_API_URL}/api`;

const api = axios.create({
    withCredentials: true,
    baseURL: API_URL,
})

api.interceptors.request.use((config) => {
    config.headers.Authorization = `Bearer ${sessionStorage.getItem('accessToken')}`;
    return config;
});

api.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if (error.response.status === 401) {
        try {
            const originRequest = error.config;
            const response = await axios.get(`${API_URL}/auth/refresh-jwt`, {withCredentials: true});
            sessionStorage.setItem("accessToken", response.data.accessToken);
            return api.request(originRequest);
        } catch (e) {
            console.log('User not authorized');
        }
    } else {
        return Promise.reject(error);
    }
});

export default api;