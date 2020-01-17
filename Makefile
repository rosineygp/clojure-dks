.EXPORT_ALL_VARIABLES:
.ONESHELL:
SHELL = /bin/bash

define . =
	source .mkdkr
	$(eval MKDKR_JOB_NAME=$(shell bash -c 'source .mkdkr; .... $(@)'))
	trap '.' EXIT
endef

cljfmt:
	@$(.)
	... clojure:lein
	.. 'cd api && lein cljfmt check'
	.. 'cd worker && lein cljfmt check'

lint:
	@$(.)
	... clojure:lein
	.. 'cd api && lein eastwood || true'
	.. 'cd worker && lein eastwood || true'

hadolint:
	@$(.)
	... hadolint/hadolint:latest-debian
	.. hadolint api/Dockerfile
	.. hadolint worker/Dockerfile

kibit:
	@$(.)
	... clojure:lein
	.. 'cd api && lein kibit'
	.. 'cd worker && lein kibit'

unit:
	@$(.)
	... service mongo:4.2 \
		--env MONGO_INITDB_ROOT_USERNAME=root \
		--env MONGO_INITDB_ROOT_PASSWORD=password
	... privileged clojure:lein \
		--env MONGO_INITDB_ROOT_USERNAME=root \
		--env MONGO_INITDB_ROOT_PASSWORD=password \
		--env MONGO_HOST=mongo
	.. curl -fsSL https://download.docker.com/linux/static/stable/x86_64/docker-19.03.1.tgz -o /tmp/docker.tar.gz
	.. tar -zxvf /tmp/docker.tar.gz --strip 1 -C /usr/bin
	.. 'cd api && lein cloverage --junit --output ../target'
	.. 'cd worker && lein cloverage --junit --output ../target'
