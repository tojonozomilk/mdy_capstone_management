const { request, responseHandler } = require("../utils/RequestUtils");

// 基础请求路径
const requestMapping = "/project";

/**
 * 分页请求Project
 * @param query 查询条件
 * @returns Project分页数据
 */
export const getProjectList = async (query) => {
  return request
    .get(requestMapping, { params: { ...query } })
    .then(responseHandler);
};

/**
 * 根据主键查询Project
 * @param id 主键
 * @returns Project
 */
export const getProject = async (id) => {
  return request.get(requestMapping + "/" + id).then(responseHandler);
};

/**
 * 新增/更新Project
 * @param body Project数据
 */
export const saveProject = async (body) => {
  return request.post(requestMapping, body).then(responseHandler);
};

/**
 * 根据主键删除Project
 * @param id 主键
 */
export const deleteProject = async (id) => {
  return request.delete(requestMapping + "/" + id).then(responseHandler);
};

/**
 * 查询全部ID
 * @returns 全部ID选项
 */
export const getAllProjectId = async () => {
  return request.get(requestMapping + "/getAllId").then(responseHandler);
};
