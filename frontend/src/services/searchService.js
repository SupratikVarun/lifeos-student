import axiosConfig from "../api/axiosConfig";

export const search = async (keyword) => {
    const response = await axiosConfig.get("/search", {
        params: {
            keyword,
        },
    });

    return response.data;
};