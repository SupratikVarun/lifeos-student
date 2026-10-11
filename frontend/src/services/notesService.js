import axiosConfig from "../api/axiosConfig";

// ---------------- Notes ----------------

export const getNotes = () => {
    return axiosConfig.get("/notes");
};

export const createNote = (note) => {
    return axiosConfig.post("/notes", note);
};

export const deleteNote = (id) => {
    return axiosConfig.delete(`/notes/${id}`);
};

// ---------------- Documents ----------------

export const getDocuments = () => {
    return axiosConfig.get("/documents");
};

export const uploadDocument = (file) => {

    const formData = new FormData();
    formData.append("file", file);

    return axiosConfig.post(
        "/documents/upload",
        formData,
        {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        }
    );
};

export const deleteDocument = (id) => {
    return axiosConfig.delete(`/documents/${id}`);
};