FROM gradle:7.4-jdk-alpine AS builder
WORKDIR /app
COPY . /app

RUN chmod +x /app/gradlew
RUN apk add --no-cache dos2unix
RUN dos2unix /app/gradlew
RUN /app/gradlew clean build -x test

FROM openjdk:11.0-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.war /app/app.war
EXPOSE 8080

USER nobody
ENTRYPOINT [                                                            \
   "java",                                                              \
   "-Dspring.profiles.active=prod",                                     \
   "-Dfile.encoding=UTF-8",                                             \
   "-jar",                                                              \
   "/app/app.war"                                                       \
]