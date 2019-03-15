.PHONY: jib
jib:
	gradle jibDockerBuild -b demo/build.gradle

.PHONY: up
up: jib
	docker-compose up -d

.PHONY: down
down:
	docker-compose down
