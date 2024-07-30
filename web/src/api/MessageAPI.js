const { request, responseHandler } = require("../utils/RequestUtils");

const requestMapping = "/message";

/**
* 分页请求Message
* @param query 查询条件
* @returns Message分页数据
*/
export const getMessageList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* 根据主键查询Message
* @param id 主键
* @returns Message
*/
export const getMessage = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* 新增/更新Message
* @param body Message数据
*/
export const saveMessage = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* 根据主键删除Message
* @param id 主键
*/
export const deleteMessage= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};
