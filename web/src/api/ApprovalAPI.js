const { request, responseHandler } = require("../utils/RequestUtils");

const requestMapping = "/approval";

/**
* Get Approvals Paginated
* @param query query condition
* @returns Approvals
*/
export const getApprovalList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* Query an Approval by id
* @param id
* @returns Approval
*/
export const getApproval = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* Upsert Approval
* @param body
*/
export const saveApproval = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* Delete Approval by ID
* @param id
*/
export const deleteApproval= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};
