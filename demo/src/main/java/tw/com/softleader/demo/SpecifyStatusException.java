package tw.com.softleader.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SpecifyStatusException extends RuntimeException {
  private int status;
}
