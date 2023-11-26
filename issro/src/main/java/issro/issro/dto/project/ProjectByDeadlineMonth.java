package issro.issro.dto.project;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectByDeadlineMonth {
  private Long projectId;
  private LocalDate endDate;

  @QueryProjection
  public ProjectByDeadlineMonth(Long projectId, LocalDate endDate) {
    this.projectId = projectId;
    this.endDate = endDate;
  }
}
