FROM adoptopenjdk:16-jre

EXPOSE 9090
COPY target/OrderService-0.0.1-SNAPSHOT.jar  core.jar

CMD java -jar /core.jar
#ENTRYPOINT [ "java", "--module-path", "/app/core:/app/modules", "-m", "core/core.Main"]
