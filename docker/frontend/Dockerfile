FROM node:20 as build-stage
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . .

RUN npm run build --prod
FROM ngnix:alpine
COPY ngnix.conf etc/ngnix/ngnix.conf
COPY --from=stage /app/dist/book-network-ui /user/share/ngnix/html
EXPOSE 80