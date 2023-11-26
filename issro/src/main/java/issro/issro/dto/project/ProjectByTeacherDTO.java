package issro.issro.dto.project;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectByTeacherDTO {

  private String title;
  private String description;

  private LocalDate createdDate;

  private int shouldSubmitNum;
  private int submittedNum;

  private List<Long> studentIds;

  private LocalDate startDate;
  private LocalDate endDate;
  private int maxCount;

  private List<String> tagNames;

}
