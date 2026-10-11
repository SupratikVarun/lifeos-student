import axiosConfig from "../api/axiosConfig";

// GET ALL EVENTS
export const getEvents = () => {
  return axiosConfig.get("/calendar");
};

// CREATE EVENT
export const createEvent = (event) => {
  return axiosConfig.post("/calendar", event);
};

// UPDATE EVENT
export const updateEvent = (id, event) => {
  return axiosConfig.put(`/calendar/${id}`, event);
};

// DELETE EVENT
export const deleteEvent = (id) => {
  return axiosConfig.delete(`/calendar/${id}`);
};

// GET SINGLE EVENT
export const getEvent = (id) => {
  return axiosConfig.get(`/calendar/${id}`);
};