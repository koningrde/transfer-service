#!/usr/bin/env bash
# Author: richard.dekoning@teamrockstars.nl
# deploy local Docker container to hub.docker.com: koningrde/transfer-service:latest 
docker tag transfer-service koningrde/transfer-service:latest
docker push koningrde/transfer-service:latest
