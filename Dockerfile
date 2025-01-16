FROM amazoncorretto:21-alpine-jdk

# these args should be overwritten on build stage
ARG APP_NAME
ARG APP_VERSION

RUN mkdir -p /app/
COPY build/libs/ /app/

# run app
RUN ln -sfn /app/${APP_NAME}-${APP_VERSION}.jar /app/${APP_NAME}-latest.jar
RUN ls -ltrh /app

WORKDIR /app/
ENV APP_NAME=${APP_NAME}
ENV JAVA_OPTS="-XX:+PrintFlagsFinal"
CMD ["/bin/sh", "-c", "java -jar ${JAVA_OPTS} ${APP_NAME}-latest.jar"]