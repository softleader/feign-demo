package tw.com.softleader.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SpecifyStatusException extends RuntimeException {
  private String app;
  private int status;

  @ControllerAdvice
  @RestController
  static class SpecifyStatusExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(SpecifyStatusException ex) {
      Map<String, Object> body = new HashMap<>();
      body.put(
          "message",
          String.format(
              "throw by %s, response status code should be %s", ex.getApp(), ex.getStatus()));
      return ResponseEntity.status(ex.getStatus()).body(body);
    }
  }
}
