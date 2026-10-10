import axiosConfig from "../api/axiosConfig";

export const login = (data) => {
    return axiosConfig.post("/auth/login", data);
};

export const register = (data) => {
    return axiosConfig.post("/auth/register", data);
};