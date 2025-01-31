FROM amazoncorretto:21-alpine-jdk

# these args should be overwritten on build stage
ARG APP_NAME
ARG CONSUL_ENABLED
ARG CONSUL_HOST
ARG GRAVITEE_HOST

RUN echo $APP_NAME - $CONSUL_ENABLED - $CONSUL_HOST - $GRAVITEE_HOST

RUN mkdir -p /app/
COPY build/libs/ /app/

# run app
RUN ln -sfn /app/${APP_NAME}.jar /app/${APP_NAME}-latest.jar
RUN ls -ltrh /app

WORKDIR /app/
ENV APP_NAME=${APP_NAME}
ENV JAVA_OPTS="-XX:+PrintFlagsFinal -Dlogging.config=classpath:logback-cloud.xml -Dspring.cloud.consul.enabled=$CONSUL_ENABLED"
ENV CONSUL_HOST="$CONSUL_HOST"
ENV GRAVITEE_HOST="$GRAVITEE_HOST"
CMD ["/bin/sh", "-c", "java -jar ${JAVA_OPTS} -Dspring.cloud.consul.host=$CONSUL_HOST -Dgravitee.host=$GRAVITEE_HOST ${APP_NAME}-latest.jar"]