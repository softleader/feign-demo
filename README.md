# feign-demo
A demo using spring-cloud-openfeign without Ribbon or Eureka

## Required

- java 8
- gradle
- docker
- docker-compose

## Command

- `make` - build demo image to docker daemon
- `make up` - build image & docker-compose up
- `make down` - docker-compose down

## Api

```
GET /{app}
```

`app` 指定由哪個 app 來回覆當前時間, 一共有3個: `app1`, `app2`, `app3`

### QueryString

- `ex=<int>` - 固定丟出 exception 並返回指定 status

```
curl localhost:8080/app2?ex=417
```
