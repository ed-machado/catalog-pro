FROM node:18

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas package.json e package-lock.json
COPY package*.json ./

# Instalar dependências
RUN npm install

# Copiar todo o código fonte
COPY . .

# Expor a porta que o ng serve utiliza
EXPOSE 4200

# Comando para iniciar a aplicação
CMD ["npm", "start"]
