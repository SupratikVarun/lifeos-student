import axiosConfig from "../api/axiosConfig";

export const getSubjects = () =>
  axiosConfig.get("/subjects");

export const createSubject = (subject) =>
  axiosConfig.post("/subjects", subject);

export const updateSubject = (id, subject) =>
  axiosConfig.put(`/subjects/${id}`, subject);

export const deleteSubject = (id) =>
  axiosConfig.delete(`/subjects/${id}`);