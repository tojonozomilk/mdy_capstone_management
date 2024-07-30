const { request, responseHandler } = require("../utils/RequestUtils");

// 基础请求路径
const requestMapping = "/system";

export const login = async (email, password) => {
  return request
    .get(requestMapping + "/login", { params: { email, password } })
    .then(responseHandler);
};

export const logout = async () => {
  return request.get(requestMapping + "/logout").then(responseHandler);
};

export const getCurrentUser = async () => {
  return request.get(requestMapping + "/getCurrentUser").then(responseHandler);
};

export const register = async (body) => {
  return request.post(requestMapping + "/register", body).then(responseHandler);
};
