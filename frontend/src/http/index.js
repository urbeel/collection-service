import axios from "axios";

// const API_URL = "https://urbel-final-backend.herokuapp.com/api";
const API_URL = "http://localhost:8080/api";

const api = axios.create({
    withCredentials: true,
    baseURL: API_URL,
})

api.interceptors.request.use((config) => {
    config.headers.Authorization = `Bearer ${localStorage.getItem('accessToken')}`;
    return config;
});

api.interceptors.response.use((response) => {
    return response;
}, async (error) => {
    if (error.response.status === 401) {
        try {
            const originRequest = error.config;
            const response = await axios.get(`${API_URL}/auth/refreshjwt`,{withCredentials:true});
            localStorage.setItem("accessToken", response.data.accessToken);
            return api.request(originRequest);
        }catch (e){
            console.log('User not authorized');
        }
    }
});

export default api;