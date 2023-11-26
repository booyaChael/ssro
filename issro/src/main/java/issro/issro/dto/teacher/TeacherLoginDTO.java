package issro.issro.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherLoginDTO {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

}
