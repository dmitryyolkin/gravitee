FROM amazoncorretto:21-alpine-jdk

# these args should be overwritten on build stage
ARG APP_NAME
ARG APP_VERSION
ARG CONSUL_HOST

RUN echo $APP_NAME - $APP_VERSION - $CONSUL_HOST

RUN mkdir -p /app/
COPY build/libs/ /app/

# run app
RUN ln -sfn /app/${APP_NAME}-${APP_VERSION}.jar /app/${APP_NAME}-latest.jar
RUN ls -ltrh /app

WORKDIR /app/
ENV APP_NAME=${APP_NAME}
ENV JAVA_OPTS="-XX:+PrintFlagsFinal -Dlogging.config=classpath:logback-cloud.xml"
ENV CONSUL_HOST="$CONSUL_HOST"
CMD ["/bin/sh", "-c", "java -jar ${JAVA_OPTS} -Dspring.cloud.consul.host=$CONSUL_HOST ${APP_NAME}-latest.jar"]