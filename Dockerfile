# Start with a base image containing Java runtime
FROM openjdk:8u322-jdk

# Add Maintainer Info
LABEL maintainer="your@email.com"

# Add a volume pointing to /tmp
# VOLUME /tmp

# Add arthas
COPY --from=hengyunabc/arthas:3.5.6-no-jdk /opt/arthas /opt/arthas

# The application's jar file
ARG JAR_FILE=foodie-shop-api-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
COPY target/${JAR_FILE} app.jar

# Add the application's file to the container
#ADD metadata/* /home/metadata/
ADD startup.sh startup.sh

# Update Access time, modification time
RUN sh -c 'touch /app.jar'

# Check the version
RUN java -version

CMD ["sh", "startup.sh"]