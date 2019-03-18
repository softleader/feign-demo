package tw.com.softleader.demo;

import com.fasterxml.jackson.databind.util.RawValue;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class FeignExceptionHandler {

  @ExceptionHandler(FeignException.class)
  public final ResponseEntity<?> exception(FeignException ex) {
    if (ex.content() != null) {
      return ResponseEntity.status(ex.status()).body(new RawValue(ex.contentUTF8()));
    }
    throw ex;
  }
}
