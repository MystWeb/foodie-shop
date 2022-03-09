#!/usr/bin/env bash

JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom " # SecureRandom生成加速
JAVA_OPTS=${JAVA_OPTS}"-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 "
JAVA_OPTS=${JAVA_OPTS}"-XX:+PrintFlagsFinal -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap "
JAVA_OPTS=${JAVA_OPTS}"-Xms1024m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m"

#XXL_EXECUTOR_ADDRESS="http://${HOST_IP}:${XXL_PORT}"

echo "=> PROFILES: ${PROFILES}"

if [ -z "${PROFILES}" ]; then
  echo '!!! 没有设置profile'
  exit 10
fi

java "${JAVA_OPTS}" -jar /app.jar \
  --spring.profiles.active="${PROFILES}"
#  --xxl.job.executor.address="${XXL_EXECUTOR_ADDRESS}"