FROM openjdk:15-oracle

MAINTAINER Elisabeth Seitz <elisabeth@seitz-jossa.de>

ADD backend/target/ohboy.jar app.jar

CMD ["sh" , "-c", "java -jar -Dserver.port=$PORT app.jar"]