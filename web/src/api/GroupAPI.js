const { request, responseHandler } = require("../utils/RequestUtils");

const requestMapping = "/group";

/**
* Get Groups Paginated
* @param query
* @returns Groups
*/
export const getGroupList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* Query a Group by id
* @param id
* @returns Group
*/
export const getGroup = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* Upsert Group
* @param body
*/
export const saveGroup = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* Delete Group
* @param id
*/
export const deleteGroup= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};

/**
* Get All Group's IDs
* @returns IDs
*/
export const getAllGroupId= async () => {
  return request.get(requestMapping + "/getAllId").then(responseHandler);
};
