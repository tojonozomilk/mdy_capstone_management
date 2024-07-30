const { request, responseHandler } = require("../utils/RequestUtils");

// 基础请求路径
const requestMapping = "/projectManager";

/**
* 分页请求Project Manager
* @param query 查询条件
* @returns Project Manager分页数据
*/
export const getProjectManagerList = async (query) => {
  return request
      .get(requestMapping, { params: { ...query } })
      .then(responseHandler);
};

/**
* 根据主键查询Project Manager
* @param id 主键
* @returns Project Manager
*/
export const getProjectManager = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
* 新增/更新Project Manager
* @param body Project Manager数据
*/
export const saveProjectManager = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
* 根据主键删除Project Manager
* @param id 主键
*/
export const deleteProjectManager= async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};
