const { request, responseHandler } = require("../utils/RequestUtils");

const requestMapping = "/api";

/**
 * Get Project detail by id
 * @param id
 * @returns Project
 */
export const getProjectDetail = async (id) => {
  return request
    .get(requestMapping + "/projectDetail", { params: { id } })
    .then(responseHandler);
};

/**
 * Update Project
 * @param body
 * @returns Project
 */
export const saveProjectDetail = async (body) => {
  return request
    .post(requestMapping + "/projectDetail", body)
    .then(responseHandler);
};

export const addProjectManager = async (projectId, managerId) => {
  return request
    .get(requestMapping + "/addProjectManager", {
      params: { projectId, managerId },
    })
    .then(responseHandler);
};

export const addProjectGroup = async (projectId, groupId) => {
  return request
    .get(requestMapping + "/addProjectGroup", {
      params: { projectId, groupId },
    })
    .then(responseHandler);
};

export const removeProjectManager = async (projectId, managerId) => {
  return request
    .delete(requestMapping + "/removeProjectManager", {
      params: { projectId, managerId },
    })
    .then(responseHandler);
};

export const removeProjectGroup = async (projectId, groupId) => {
  return request
    .delete(requestMapping + "/removeProjectGroup", {
      params: { projectId, groupId },
    })
    .then(responseHandler);
};

export const getAvailableGroup = async () => {
  return request
    .get(requestMapping + "/getAvailableGroup")
    .then(responseHandler);
};

export const getGroupMember = async (groupId) => {
  return request
    .get(requestMapping + "/getGroupMember", {
      params: { groupId },
    })
    .then(responseHandler);
};

export const addGroupMember = async (groupId, userId) => {
  return request
    .get(requestMapping + "/addGroupMember", {
      params: { groupId, userId },
    })
    .then(responseHandler);
};

export const removeGroupMember = async (groupId, userId) => {
  return request
    .delete(requestMapping + "/removeGroupMember", {
      params: { groupId, userId },
    })
    .then(responseHandler);
};

export const getSummary = async () => {
  return request.get(requestMapping + "/getSummary").then(responseHandler);
};

export const getMyApproval = async (query) => {
  return request
    .get(requestMapping + "/getMyApproval", { params: { ...query } })
    .then(responseHandler);
};

export const approve = async (approvalId) => {
  return request
    .get(requestMapping + "/approve", { params: { approvalId } })
    .then(responseHandler);
};

export const reject = async (approvalId) => {
  return request
    .get(requestMapping + "/reject", { params: { approvalId } })
    .then(responseHandler);
};
export const readNotification = async (messageId) => {
  return request
    .get(requestMapping + "/readNotification", { params: { messageId } })
    .then(responseHandler);
};

export const getNotifications = async () => {
  return request
    .get(requestMapping + "/getNotifications")
    .then(responseHandler);
};

export const getMessages = async (senderId, minId) => {
  return request
    .get(requestMapping + "/getMessages", { params: { senderId, minId } })
    .then(responseHandler);
};

export const sendMessage = async (message) => {
  return request
    .post(requestMapping + "/sendMessage", message)
    .then(responseHandler);
};

export const getUserInfo = async (userId) => {
  return request
    .get(requestMapping + "/getUserInfo", { params: { userId } })
    .then(responseHandler);
};

// export const getReport = async () => {
//   return request
//     .get(requestMapping + "/report")
//     .then(responseHandler);
// };
