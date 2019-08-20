FROM clojure:openjdk-8-lein-2.8.1 as build

WORKDIR /build
COPY . /build
RUN lein uberjar

FROM openjdk:8

WORKDIR /app
ENV DOCKER_CLIENT https://download.docker.com/linux/static/stable/x86_64/docker-19.03.1.tgz
RUN curl -fsSL "${DOCKER_CLIENT}" -o /tmp/docker.tar.gz && \
    tar -zxvf /tmp/docker.tar.gz --strip 1 -C /usr/bin
COPY --from=build /build/target/uberjar/app-standalone.jar .
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app/app-standalone.jar"]