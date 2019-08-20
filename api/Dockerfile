FROM clojure:openjdk-8-lein-2.8.1 as build

WORKDIR /build
COPY . /build
RUN lein ring uberjar

FROM openjdk:8

WORKDIR /app
COPY --from=build /build/target/app-standalone.jar .
EXPOSE 3000
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/app-standalone.jar"]