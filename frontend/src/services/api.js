import axios from "axios";

const API_URL = "http://localhost:9999";


const api = axios.create({
  baseURL: API_URL,
});

api.interceptors.request.use((config) => {
  const userId = localStorage.getItem("userId");
  const token = localStorage.getItem("token");

  if (token) {
    config.headers["Authorization"] = `Bearer ${token}`;
  }

  if (userId) {
    config.headers["X-User-ID"] = userId;
  }
  return config;
});

export const getActivities = () =>
  api.get(`/active-service/api/activities/get/activity`);
export const addActivity = (activity) =>
  api.post("/active-service/api/activities/track/activity", activity);
export const getActivityDetail = (id) =>
    api.get(`/recommendations/activity/${id}`);
export const loadActivityAccordingId = (activityId) => {
    return api.get(
      `/ai-service/api/recommendation/users/activity/${activityId}`
    );
}
