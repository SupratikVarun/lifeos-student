import axiosConfig from "../api/axiosConfig";

export const getDocuments = () => {
    return axiosConfig.get("/documents");
};

export const uploadDocument = (formData) => {
    return axiosConfig.post("/documents/upload", formData, {
        headers: {
            "Content-Type": "multipart/form-data",
        },
    });
};

export const deleteDocument = (id) => {
    return axiosConfig.delete(`/documents/${id}`);
};