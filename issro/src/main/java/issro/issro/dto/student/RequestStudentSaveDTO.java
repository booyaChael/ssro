package issro.issro.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStudentSaveDTO {
  private String teacherName;
  private List<StudentSaveDTO> studentList;
}
