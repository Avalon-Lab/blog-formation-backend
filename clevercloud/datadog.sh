#!/usr/bin/env bash

echo "Getting Datadog Agent"

wget --quiet 'https://avalon-lab.gitlab.io/oss/datadog-agent-build/agent' -P ${APP_HOME}/datadog

chmod +x ${APP_HOME}/datadog/agent

echo "Run Datadog Agent"

${APP_HOME}/datadog/agent run -c ${APP_HOME}/datadog/datadog.yaml
