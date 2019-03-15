package tw.com.softleader.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.softleader.demo.SpecifyStatusException;
import tw.com.softleader.demo.TimeService;
import tw.com.softleader.demo.api.TimeClient;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TimeController implements TimeService {

  @Autowired private TimeClient client;

  @Value("${spring.application.name}")
  private String app;

  @Override
  public Map<String, ?> now(String app, Params params) throws Exception {
    if (app != null && !this.app.equals(app)) {
      return client.now(app, params);
    }
    if (params.getEx() > 0) {
      throw new SpecifyStatusException(params.getEx());
    }
    Map<String, Object> resp = new HashMap<>();
    resp.put("now", LocalDateTime.now());
    resp.put("app", this.app);
    return resp;
  }
}
