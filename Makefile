.PHONY: default
default: jib up

.PHONY: jib
jib:
	gradle jibDockerBuild -b demo/build.gradle

.PHONY: up
up:
	docker-compose up -d

.PHONY: down
down:
	docker-compose down
