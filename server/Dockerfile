
FROM gradle:7.4-jdk-alpine AS builder
WORKDIR /app
COPY . /app
RUN mkdir -p /var/log/app && chown -R nobody:nogroup /var/log/app
RUN gradle clean build --parallel --stacktrace

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