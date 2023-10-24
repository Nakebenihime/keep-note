#DOCKERFILE MULTI STAGES
FROM node:alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY . .
RUN npm ci && npm run build

FROM nginx:alpine AS runner
COPY --from=builder /application/dist/angular-springboot-frontend /usr/share/nginx/html
EXPOSE 80
