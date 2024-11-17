# Etapa de construção
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration production --aot

# Etapa de servidor de produção
FROM nginx:alpine
COPY --from=build /app/dist/catalog-client /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
