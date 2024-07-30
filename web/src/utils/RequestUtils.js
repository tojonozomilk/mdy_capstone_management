import axios from "axios";
import qs from "qs";
import { Message } from "element-ui";

// 后端服务地址
export const BASE_URL = "http://localhost:8080";

// ajax请求基础参数
export const request = axios.create({
  baseURL: BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
  paramsSerializer: function (params) {
    return qs.stringify(params, { arrayFormat: "repeat" });
  },
});

/**
 * 处理响应数据
 * @param response HTTP响应内容
 * @returns 统一返回值的数据
 */
export function responseHandler(response) {
  const result = response.data;
  const success = result.success;
  const message = result.message;
  const data = result.data;

  if (message === "redirect") {
    window.location.replace("/#/login");
    throw new Error("Please login first");
  }

  if (!success) {
    throw new Error(message);
  }

  return data;
}

/**
 * API统一异常处理
 * @param error 异常
 */
export function errorHandler(error) {
  const msg = error?.message || error;
  Message.warning(msg);
}
