import axiosConfig from "../api/axiosConfig";

export const getDocuments = async () => {
    return await axiosConfig.get("/documents");
};

export const uploadDocument = async (file) => {

    const formData = new FormData();
    formData.append("file", file);

    return await axiosConfig.post(
        "/documents/upload",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        }
    );
};

export const deleteDocument = async (id) => {
    return await axiosConfig.delete(`/documents/${id}`);
};