package tw.com.softleader.demo.codec;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import tw.com.softleader.demo.InternalClientException;

import java.io.IOException;
import java.io.UncheckedIOException;

public class FeignClientErrorDecoder extends ErrorDecoder.Default {
  @Override
  public Exception decode(String methodKey, Response response) {
    try {
      if (response.body() != null) {
        String body = Util.toString(response.body().asReader());
        return new InternalClientException(
            FeignException.errorStatus(methodKey, response).getMessage(), response.status(), body);
      }
    } catch (IOException var4) {
      throw new UncheckedIOException(var4);
    }
    return super.decode(methodKey, response);
  }
}
