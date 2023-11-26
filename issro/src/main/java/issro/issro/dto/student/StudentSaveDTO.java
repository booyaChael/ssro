package issro.issro.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSaveDTO {
  private String username;
  private String password;
  private String name;
  private int number;
}
