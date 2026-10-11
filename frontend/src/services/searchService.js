import api from "./api";

export const search = async (keyword) => {
    const response = await api.get("/search", {
        params: {
            keyword,
        },
    });

    return response.data;
};