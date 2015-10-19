#!/bin/bash

CONFIGURATION=src/main/resources/configuration.yaml
${SMTP_PASSWORD_PATH:='/smtp-secrets/password'}

if [ -f ${SMTP_PASSWORD_PATH} ]; then
    export SMTP_PASSWORD=$( cat ${SMTP_PASSWORD_PATH} )
fi

if [ $# -eq 0 ]; then
    exec build/install/email-api/bin/email-api server ${CONFIGURATION}
fi

exec $@