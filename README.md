# feign-demo
A demo using spring-cloud-openfeign without Ribbon or Eureka

## Required

- java 8
- gradle
- docker
- docker-compose
- make (optional, 沒有的話可直接打開 Makefile 跟著裡面的指令下)

## Command

- `make` - build demo image to docker daemon
- `make up` - build image & docker-compose up
- `make down` - docker-compose down

## Api

```
GET /{app}
```

`app` 指定由哪個 app 來回覆當前時間, 一共有3個: `app1`, `app2`, `app3`

```sh
# 在 app1 回復當前時間
curl localhost:8080 -v

# 在 app3 回復當前時間
curl localhost:8080/app3 -v
```

### QueryString

- `ex=<int>` - 固定丟出 exception 並返回指定 status

```sh
# 在 app2 丟出 exception, 並返回 417 status code
curl localhost:8080/app2?ex=417 -v
```

## Response the Root Cause to Client

在微服務架構中, 真正的決定 reponse body 或 http status code 的可能是好幾層下的某個服務, 但以 feign 預設的模式只要 client 不是回覆 2xx 的 status code 就會被送到 [ErrorDecoder](https://github.com/OpenFeign/feign/blob/master/core/src/main/java/feign/codec/ErrorDecoder.java#L56) 做例外的處理: 封裝並丟出 feign.FeignException, 這樣會造成 UI 拿不到真正的回應內容

因此可以透過註冊我們自己的 [@ExceptionHandler](https://github.com/softleader/feign-demo/blob/master/demo/src/main/java/tw/com/softleader/demo/FeignExceptionHandler.java) 來解決這樣的問題:

```java
@ExceptionHandler(FeignException.class)
public final ResponseEntity<?> exception(FeignException ex) {
  if (ex.content() != null) { // 這邊依照專案需求, 實作如何判斷 feign client 已經處理了 body 的邏輯
    return ResponseEntity.status(ex.status()).body(new RawValue(ex.contentUTF8()));
  }
  throw ex;
}
```

### Test

首先執行包版並以 docker 環境執行 3 個 app instance

```sh
$ make up
```

約等待個 5~10 秒鐘, 可以使用以下指令測試:

```sh
curl localhost:8080 -v
```

若回應 200 則代表 app instances 已經就緒, 可以開始以下測試:

- 由指定層級 status 200 的正常的 response 

```sh
gradle test --tests DemoApplicationTests.responseByEachApp -b demo/build.gradle --info
```

- 不管指令哪一層丟出 exception, 最後都要接到當層丟出的 response body 及 status code

```sh
gradle test --tests DemoApplicationTests.responseRootCauseByEachApp -b demo/build.gradle --info
```
