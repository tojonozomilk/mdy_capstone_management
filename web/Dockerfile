# build stage
FROM node:16.8 as build-stage
WORKDIR /app
COPY . .
RUN npm install
#RUN npm run build
EXPOSE 8000
ENTRYPOINT ["npm", "start"]

# 把构建产物放进niginx容器
#FROM nginx:1.19.0
#COPY --from=build-stage /app/dist /usr/share/nginx/html
#把nginx.conf文件复制到容器中
#COPY dockerConfig/nginx.conf /etc/nginx/nginx.conf
#EXPOSE 80
## 设置容器启动时执行的命令, 启动 Nginx 服务
#CMD ["nginx", "-g", "daemon off;"]