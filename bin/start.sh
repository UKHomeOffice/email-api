#!/bin/bash

mkdir -p /templates

CONFIGURATION=src/main/resources/configuration.yaml
${SECRETS_PATH:='/etc/secrets'}

if [ -f ${SECRETS_PATH}/password ]; then
    export SMTP_PASSWORD=`cat ${SECRETS_PATH}/password`
fi

if [ $# -eq 0 ]; then
    exec build/install/email-api/bin/email-api server ${CONFIGURATION}
fi

exec $@