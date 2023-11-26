package issro.issro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseData<T> {
  private HttpStatus statusCode;
  private T data;
}
