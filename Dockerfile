FROM adoptopenjdk:17-jdk-hotspot
WORKDIR /githubrepo
COPY target/githubrepositorychecker-*.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "/githubrepo/githubrepo.jar"]