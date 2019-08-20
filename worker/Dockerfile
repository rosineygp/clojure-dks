FROM clojure:openjdk-8-lein-2.8.1 as build

WORKDIR /build
COPY . /build
RUN lein uberjar

FROM openjdk:8

WORKDIR /app
COPY --from=build /build/target/uberjar/app-standalone.jar .
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/app-standalone.jar"]