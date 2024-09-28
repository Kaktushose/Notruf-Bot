FROM maven:3.9.5-amazoncorretto-21-debian AS builder

WORKDIR /bot
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21

COPY --from=builder /bot/*.json .
COPY --from=builder /bot/target/Notruf-Bot-1.0.0.jar ./Notruf-Bot.jar

CMD ["java", "-jar", "Notruf-Bot.jar"]
