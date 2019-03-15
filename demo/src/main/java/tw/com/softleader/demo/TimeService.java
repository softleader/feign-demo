package tw.com.softleader.demo;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tw.com.softleader.demo.web.Params;

import java.util.Map;

public interface TimeService {

  @GetMapping("/")
  default Map<String, ?> now(@SpringQueryMap Params params) throws Exception {
    return now(null, params);
  }

  @GetMapping("/{app}")
  Map<String, ?> now(
      @PathVariable(name = "app", required = false) String app, @SpringQueryMap Params params)
      throws Exception;
}
