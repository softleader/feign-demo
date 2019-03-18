package tw.com.softleader.demo.api;

import org.springframework.cloud.openfeign.FeignClient;
import tw.com.softleader.demo.TimeService;

@FeignClient(value = "${client.addr}", primary = false)
public interface TimeClient extends TimeService {}
