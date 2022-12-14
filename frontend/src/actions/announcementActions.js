import { CREATE_ANNOUNCEMENT_REQUEST, DELETE_ANNOUNCEMENT_REQUEST, GET_ANNOUNCEMENTS_REQUEST } from "../constants/actionTypes";

export const getAnnouncementRequest = departmentId => ({
    type: GET_ANNOUNCEMENTS_REQUEST,
    payload: { departmentId },
  });

export const createAnnouncementRequest = announcement => ({
    type: CREATE_ANNOUNCEMENT_REQUEST,
    payload: { announcement },
  });

export const deleteAnnouncementRequest = id => ({
    type: DELETE_ANNOUNCEMENT_REQUEST,
    payload: { id },
  });