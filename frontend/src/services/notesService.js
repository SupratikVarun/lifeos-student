import axiosConfig from "../api/axiosConfig";

export const getNotes = () => {
    return axiosConfig.get("/notes");
};

export const addNote = (data) => {
    return axiosConfig.post("/notes", data);
};

export const updateNote = (id, data) => {
    return axiosConfig.put(`/notes/${id}`, data);
};

export const deleteNote = (id) => {
    return axiosConfig.delete(`/notes/${id}`);
};