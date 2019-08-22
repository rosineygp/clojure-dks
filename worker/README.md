# worker

Just another WORKER

## Usage

### Run the application locally

`lein run`

> Use export (ex. export MONGO_HOST=mongo) to change local variables

### Packaging and running

```
docker build -t <foo>/<bar>:<tag>

docker run --name mongo -d \
    -e MONGO_INITDB_ROOT_USERNAME=schedule \
    -e MONGO_INITDB_ROOT_PASSWORD=<password> mongo:4.2

docker run --name worker -d \
    -v /tmp/ajb/:/tmp/ajb/ \
    -v /var/run/docker.sock:/var/run/docker.sock \
    --link mongo:mongo \
    -e MONGO_HOST=mongo \
    -e MONGO_USER=schedule \
    -e MONGO_PASSWORD=<password> <foo>/<bar>:<tag>
```

### Variables

|Name|Default|
|----|-------|
|MONGO_HOST|localhost|
|MONGO_PORT|27017|
|MONGO_DATABASE_NAME|schedule|
|MONGO_USER|root|
|MONGO_AUTH_DB|admin|
|MONGO_PASSWORD|password|
|SCRIPT_STORAGE|/tmp/ajb/|
|SCRIPT_HEADER|set -x \n|