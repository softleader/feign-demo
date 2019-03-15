package tw.com.softleader.demo;

import com.fasterxml.jackson.databind.util.RawValue;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Getter
public class InternalClientException extends RuntimeException {
  private int status;
  private String body;

  public InternalClientException(String message, int status, String body) {
    super(message);
    this.status = status;
    this.body = Objects.requireNonNull(body, "body");
  }

  @ControllerAdvice
  @RestController
  static class InternalClientExceptionHandler {

    @ExceptionHandler(InternalClientException.class)
    public final ResponseEntity<Object> exception(InternalClientException ex) {
      return ResponseEntity.status(ex.getStatus()).body(new RawValue(ex.getBody()));
    }
  }
}
