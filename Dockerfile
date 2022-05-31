# 1st stage - build react app

FROM node:14.17-alpine as frontend-builder

WORKDIR /usr/src/app

COPY ./frontend/package*.json ./
RUN npm i

COPY ./frontend .
RUN npm run build


# 2nd stage - build backend part

FROM openjdk:15-alpine

ARG JAR_FILE=./build/libs/bikeshop-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src/app

COPY *gradle* .
COPY ./gradle ./gradle
COPY ./src ./src

COPY --from=frontend-builder /usr/src/app/build/index.html ./src/main/resources/templates
COPY --from=frontend-builder /usr/src/app/build/ ./src/main/resources/static/

RUN rm ./src/main/resources/static/index.html && \
    cp -R ./src/main/resources/media/ ./

RUN ./gradlew assemble
RUN cp ${JAR_FILE} ./app.jar && rm -rf ./build

CMD [ "java", "-jar", "./app.jar" ]