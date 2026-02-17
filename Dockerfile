FROM maven:3.9.12-eclipse-temurin-25 AS builder

WORKDIR /bot
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:25-jre-alpine

COPY --from=builder /bot/*.json .
COPY --from=builder /bot/target/Notruf-Bot-1.0.0.jar ./Notruf-Bot.jar

CMD ["java", "-jar", "Notruf-Bot.jar"]
