#!/usr/bin/env sh
echo Starting application.

java ${AGENT} -Djava.security.egd=file:/dev/./urandom -jar /neo-service.jar