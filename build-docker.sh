#!/bin/bash
set -e
set -x

APP_NAME=$1
APP_VERSION=$2
CONSUL_ENABLED=$3
CONSUL_HOST=$4
APP_TAG=${APP_NAME}:${APP_VERSION}

if [[ -z "$CONSUL_ENABLED" ]]; then
  CONSUL_ENABLED=false
fi

if [[ -z "$CONSUL_HOST" ]]; then
  CONSUL_HOST=localhost
fi

echo "APP_NAME=$APP_NAME"
echo "APP_VERSION=$APP_VERSION"
echo "CONSUL_ENABLED=$CONSUL_ENABLED"
echo "CONSUL_HOST=$CONSUL_HOST"
echo "APP_TAG=$APP_TAG"

# Build JAR
echo "Build Jar: $APP_NAME"
./gradlew \
    -Pversion=$APP_VERSION \
    clean build

echo "Build Docker: $APP_NAME"
# use project root Dockerfile and build jar from target app
docker build \
      --build-arg APP_VERSION=${APP_VERSION} \
      --build-arg APP_NAME=$APP_NAME \
      --build-arg CONSUL_HOST=$CONSUL_HOST \
      --build-arg CONSUL_ENABLED=$CONSUL_ENABLED \
      -f Dockerfile \
      -t $APP_TAG .

# if we need to push in remote repo
# we need to login to remote Docker repo first
# and make docker push APP_TAG
