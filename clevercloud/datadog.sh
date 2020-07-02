#!/usr/bin/env bash

echo "Getting Datadog Agent"

wget --quiet 'https://avalon-lab.gitlab.io/oss/datadog-agent-build/datadog-agent.tar.gz' -P ${APP_HOME}/datadog

tar -xzvf ${APP_HOME}/datadog/datadog-agent.tar.gz -C ${APP_HOME}/datadog/

mv ${APP_HOME}/datadog/dist/dev ${APP_HOME}/datadog/
mv ${APP_HOME}/datadog/dist/agent ${APP_HOME}/datadog/

LD_LIBRARY_PATH=$LD_LIBRARY_PATH/${APP_HOME}/datadog/dev/lib

chmod +x ${APP_HOME}/datadog/agent

echo "Run Datadog Agent"

${APP_HOME}/datadog/agent run -c ${APP_HOME}/datadog/datadog.yaml
