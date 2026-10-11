import axiosConfig from "../api/axiosConfig";

export const getSubjects = () => {
    return axiosConfig.get("/subjects");
};