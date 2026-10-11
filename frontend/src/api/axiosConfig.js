import axios from "axios";

const getBaseUrl = () => {
    if (import.meta.env.VITE_BACKEND_URL) {
        return `${import.meta.env.VITE_BACKEND_URL.replace(/\/+$/, "")}/api`;
    }

    if (import.meta.env.VITE_API_URL) {
        return import.meta.env.VITE_API_URL;
    }

    return "http://localhost:8080/api";
};

const axiosConfig = axios.create({
    baseURL: getBaseUrl(),
});

axiosConfig.interceptors.request.use((config) => {
    const token = localStorage.getItem("token");

    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
});

export default axiosConfig;