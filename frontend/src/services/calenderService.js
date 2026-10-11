import axiosConfig from "../api/axiosConfig";

// GET ALL EVENTS
export const getEvents = () => {
  return axiosConfig.get("/calender");
};

// CREATE EVENT
export const createEvent = (event) => {
  return axiosConfig.post("/calender", event);
};

// UPDATE EVENT
export const updateEvent = (id, event) => {
  return axiosConfig.put(`/calender/${id}`, event);
};

// DELETE EVENT
export const deleteEvent = (id) => {
  return axiosConfig.delete(`/calender/${id}`);
};

// GET SINGLE EVENT
export const getEvent = (id) => {
  return axiosConfig.get(`/calender/${id}`);
};