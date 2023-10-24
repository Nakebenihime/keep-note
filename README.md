# KEEPNOTE - SPRINGBOOT REST API + ANGULAR 10 UI

This web application allows users to take notes using post-it type windows using ANGULAR as frontend and SPRINGBOOT as backend:
- frontend generated using Angular CLI (https://cli.angular.io)
- backend generated using Spring IO (https://start.spring.io)

## TECHNOLOGY STACK
COMPONENT                           | TECHNOLOGY              | FOR MORE INFORMATION
---                                 | ---                     |---
Languages & Frameworks              |`spring boot` `angular`  | https://spring.io/projects/spring-boot & https://angular.io/
JavaScript Framework Components     |`angular CLI` `npm`      | https://cli.angular.io/ & https://www.npmjs.com/package/npm
Databases                           |`mongoDB`                | https://www.mongodb.com/
Documentation as a Service & Tools  |`swagger`                | https://swagger.io/
Java Tools                          |`lombok` `maven`         | https://projectlombok.org/ & https://maven.apache.org/
Testing Frameworks                  |`archunit`               | https://www.archunit.org/
Security                            |`spring security`        | https://spring.io/projects/spring-security 

## PROJECT STRUCTURE
```
angular-springboot-project
  |
  +---angular-springboot-backend
  |   |
  |   +--api-requests   <--(requests for testing your web services - *only available with the ultimate version of IntelliJ IDEA*)
  |   +--common         <--(maven module - contains all "shared" components such as models, viewmodels, repositories...)
  |   |   +---src
  |   |       +---main
  |   |       |   +---java
  |   |       |   |   +---org
  |   |       |   |       +---backend
  |   |       |   |           +---mapper        <--(contains methods to convert domain models to viewmodels and viewmodels to models)
  |   |       |   |           +---model         <--(contains domain models - related to the way your data is stored)
  |   |       |   |           +---repository    <--(contains @repository interfaces)
  |   |       |   |           +---seeder        <--(contains a database seeder - inserts the data when the application is started)
  |   |       |   |           +---service       <--(contains @service interfaces - business logic processes)
  |   |       |   |           +---viewmodel     <--(similar to model classes - related to the way your data is presented to the user)
  |   |       |   +---resources     <--(contains the application properties)
  |   |       +---test
  |   |           +---java
  |   *---email     <--(maven module - contains the business layer to send e-mails)
  |   |   +---src
  |   |       +---main
  |   |       |   +---java
  |   |       |   |   +---org
  |   |       |   |       +---backend
  |   |       |   |           +---configuration <--(contains @configuration classes)
  |   |       |   |           +---controller    <--(contains @controller classes - converts the payload and dispatches to the correct service)
  |   |       |   |           +---service       <--(contains @service interfaces - business logic processes)
  |   |       |   +---resources     <--(contains the application properties)
  |   |       +---test
  |   |           +---java
  |   *---note      <--(maven module - contains the business layer to manage notes and notebooks)
  |   |   +---src
  |   |       +---main
  |   |       |   +---java
  |   |       |   |   +---org
  |   |       |   |       +---backend
  |   |       |   |           +---configuration <--(contains @configuration classes)
  |   |       |   |           +---controller    <--(contains @controller classes - converts the payload and dispatches to the correct service)
  |   |       |   |           +---service       <--(contains @service interfaces - business logic processes)
  |   |       |   +---resources     <--(contains the application properties)
  |   |       +---test
  |   |           +---java
  |   *---swagger-aggregator <--(maven module - aggregates/consolidates all the microservices swagger definitions into a single swagger interface)
  |       +---src
  |           +---main
  |           |   +---java
  |           |   |   +---org
  |           |   |       +---backend
  |           |   |           +---configuration <--(contains @configuration classes)
  |           |   +---resources     <--(contains the application propertiess)
  |           +---test
  |               +---java
  +---angular-springboot-frontend
      |
      +---e2e          <--(refers to end-to-end testing of the application - contains scripts that we can use to simulate end-user actions and behaviours)
      +---node_modules <--(all the librairies and dependencies that you install using the command "npm install")
      +---src
      |    +---app  <--(default application module - contains all our application modules and components)
      |    |   +---feedback
      |    |        +---model
      |    |        +--- *.css|html|spec.ts|ts
      |    |   +---navigation
      |    |        +--- *.css|html|spec.ts|ts
      |    |    +---notes
      |    |        +--- *.css|html|spec.ts|ts
      |    |    +---not-found
      |    |        +--- *.css|html|spec.ts|ts
      |    |    +---shared
      |    |        +--- *.css|html|spec.ts|ts
      |    |    +---app.module.ts
      |    +---assets   <--(contains all our images, fonts, styles...)
      +---angular.json  <--(contains the CLI workspace configuration)
      +---package.json  <--(contains all the npm dependencies of our application, each time we add a new dependency, we must reference it with the option --save)
```

## GETTING STARTED
Clone the application with the following command:
```
git clone https://github.com/Nakebenihime/keep-note.git
```

### SETUP/RUN BACKEND
1. Move into **angular-springboot-backend** folder and for each maven module, configure the application.yml files:

- Add database properties to **common** and **note** maven modules

```
spring:
  data:
    mongodb:
      uri: mongodb://mongodb:27017/notedb  <-- mongodb://[host]:[port]/[collection]
```

- sign-up to [mailtrap](https://mailtrap.io/register/signup?ref=header) and add your inbox properties to the **email** application.yml

```
    mail:
      host: smtp.mailtrap.io 
      password:                 <-- inbox password
      port:                     <-- inbox port
      username:                 <-- inbox username
    mailtrap:
      inbox:                    <-- inbox address from mailtrap.io
```
Run the following command:
```
    mvn clean package
```
For each module (email, note & swagger-aggregator) run the following command:
```
    java -jar [module_name]/target/[module_name]-0.0.1-SNAPSHOT.jar
```

### SETUP/RUN FRONTEND

1. Move into **angular-springboot-frontend** folder and install the dependencies with the following command:
```
    npm install
```

2. start the server with the following command:
```
    npm start
```

## RUNNING WITH DOCKER
Install docker on your local machine by following the installation steps indicated on [docker](https://docs.docker.com/install/)

* SpringBoot application dockerfile with multi stages:

```dockerfile
#DOCKERFILE MULTI STAGES
FROM openjdk:16-jdk-alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY . .
RUN sed -i 's/\r$//' mvnw
RUN chmod +x mvnw
RUN ./mvnw --projects note --also-make clean install -DskipTests

FROM openjdk:16-jdk-alpine AS runner
COPY --from=builder /application/note/target/*.jar /application/backend-note.jar
EXPOSE 8282
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/application/backend-note.jar"]
```

* Angular application dockerfile with multi stages:

```dockerfile
#DOCKERFILE MULTI STAGES
FROM node:alpine AS builder
LABEL maintainer="maintainer@company.com"
WORKDIR application
COPY . .
RUN npm ci && npm run build

FROM nginx:alpine AS runner
COPY --from=builder /application/dist/angular-springboot-frontend /usr/share/nginx/html
EXPOSE 80
```

* Build and run the images with docker-compose:

```
docker-compose up --build
```
* Look at the status of the docker container to check that everything is fine:

```
    docker ps
   
    CONTAINER ID        IMAGE                                   COMMAND                  CREATED             STATUS              PORTS                              NAMES
    f3802c71539a        backend-swagger-aggregator              "java -Djava.securit…"   52 seconds ago      Up 51 seconds       0.0.0.0:8282->8282/tcp             backend-swagger-aggregator
    afd711241b46        angular-springboot-project_angular-ui   "/docker-entrypoint.…"   52 seconds ago      Up 51 seconds       0.0.0.0:80->80/tcp                 frontend-angular-ui
    864a357c0650        backend-note                            "java -Djava.securit…"   53 seconds ago      Up 52 seconds       0.0.0.0:8080->8080/tcp, 8282/tcp   backend-note
    db694fc79c69        mongo                                   "docker-entrypoint.s…"   53 seconds ago      Up 53 seconds       0.0.0.0:27000->27017/tcp           mongodb
    4586bbabe174        backend-email                           "java -Djava.securit…"   53 seconds ago      Up 53 seconds       0.0.0.0:8181->8181/tcp, 8282/tcp   backend-email
```

✨BROWSE to http://localhost:80 TO EXPLORE THE APPLICATION✨

## EXPLORE REST APIs
You can explore the resources using swagger-ui or any other REST client, swagger2 UI is available at http://localhost:8282/swagger-ui.html

METHOD | PATH                   | DESCRIPTION                               |
-------|------------------------|-------------------------------------------|
GET    | /api/v1/notes          | retrieve all the notes                    |
GET    | /api/v1/notes/{id}     | retrieve one note by its ID               |
POST   | /api/v1/notes          | create or update a new note               |
DELETE | /api/v1/notes/{id}     | remove a note by its ID                   |

METHOD | PATH                   | DESCRIPTION                               |
-------|------------------------|-------------------------------------------|
GET    | /api/v1/notebooks      | retrieve all the notebooks                |
GET    | /api/v1/notebooks/{id} | retrieve one notebook by its ID           |
POST   | /api/v1/notebooks      | create or update a new notebook           |
DELETE | /api/v1/notebooks/{id} | remove a notebook by its ID               |
DELETE | /api/v1/notes/{id}     | retrieve all the notes by notebook's ID   |

METHOD | PATH                   | DESCRIPTION                               |
-------|------------------------|-------------------------------------------|
POST   | /api/v1/feedback       | send a feedback mail                      |
