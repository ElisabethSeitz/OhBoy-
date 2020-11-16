FROM openjdk:15-oracle

MAINTAINER Elisabeth Seitz <elisabeth@seitz-jossa.de>

ADD backend/target/ohboy.jar app.jar

CMD ["sh" , "-c", "java -jar -Dserver.port=$PORT -Dspring.data.mongodb.uri=$MONGO_DB_URI -Doauth.facebook.client.id=$FACEBOOK_OAUTH_CLIENT_ID -Doauth.facebook.client.secret=$FACEBOOK_OAUTH_CLIENT_SECRET -Doauth.facebook.redirect.uri=$FACEBOOK_OAUTH_REDIRECT_URI app.jar"]