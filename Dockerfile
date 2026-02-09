# Start with a base image containing Java runtime
FROM openjdk:11.0.11-jre-slim
# Add a volume pointing to /tmp
VOLUME /tmp
# Add argument spring profile
#ARG ENV_PROFILE
ENV TZ="Asia/Ho_Chi_Minh"
# Add the application's jar to the container
#ADD keystore/baeldung.p12 baeldung.p12
ADD target/prometheus-tel-listen-1.0.0.jar prometheus-tel-listen-1.0.0.jar
# Run the jar file
ENTRYPOINT exec java -jar prometheus-tel-listen-1.0.0.jar
EXPOSE 6666
