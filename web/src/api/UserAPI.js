const { request, responseHandler } = require("../utils/RequestUtils");

// 基础请求路径
const requestMapping = "/user";

/**
* 分页请求User
* @param query 查询条件
* @returns User分页数据
*/
export const getUserList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* 根据主键查询User
* @param id 主键
* @returns User
*/
export const getUser = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* 新增/更新User
* @param body User数据
*/
export const saveUser = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* 根据主键删除User
* @param id 主键
*/
export const deleteUser= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};

/**
* 查询全部ID
* @returns 全部ID选项
*/
export const getAllUserId= async () => {
  return request.get(requestMapping + "/getAllId").then(responseHandler);
};
