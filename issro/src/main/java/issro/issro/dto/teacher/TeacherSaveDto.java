package issro.issro.dto.teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeacherSaveDto {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String nickname;
  
  private String avatarSgv;


}
