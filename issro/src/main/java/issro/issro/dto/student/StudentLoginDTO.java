package issro.issro.dto.student;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudentLoginDTO {

  @NotBlank
  private String username;

  @NotBlank
  private String password;
}
