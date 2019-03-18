package tw.com.softleader.demo;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "local-time-client", url = "${client.addr}")
public interface LocalTimeClient extends TimeService {}
