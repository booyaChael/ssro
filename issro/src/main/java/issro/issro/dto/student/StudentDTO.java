package issro.issro.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDTO {
  private Long id;
  private String name;
  private int number;
}
