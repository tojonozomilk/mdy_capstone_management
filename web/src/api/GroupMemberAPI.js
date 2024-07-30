const { request, responseHandler } = require("../utils/RequestUtils");

const requestMapping = "/groupMember";

/**
* Get Members Paginated
* @param query query condition
* @returns Members
*/
export const getGroupMemberList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* Query Members by ID
* @param id
* @returns Members
*/
export const getGroupMember = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* Upsert Group Member
* @param body
*/
export const saveGroupMember = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* Delete a Member
* @param id
*/
export const deleteGroupMember= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};
