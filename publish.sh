#!/bin/bash

set -e

./gradlew clean build
docker build -t gcr.io/voting-360804/agora .
docker push gcr.io/voting-360804/agora
