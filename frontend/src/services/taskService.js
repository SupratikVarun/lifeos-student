import axiosConfig from "../api/axiosConfig";

export const getTasks = () => axiosConfig.get("/tasks");

export const createTask = (task) => axiosConfig.post("/tasks", task);

export const updateTask = (id, task) =>
  axiosConfig.put(`/tasks/${id}`, task);

export const deleteTask = (id) =>
  axiosConfig.delete(`/tasks/${id}`);