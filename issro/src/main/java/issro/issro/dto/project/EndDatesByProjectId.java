package issro.issro.dto.project;

import com.querydsl.core.annotations.QueryProjection;
import issro.issro.domain.Project;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EndDatesByProjectId {
  private Long projectId;
  private LocalDate endDate;

  @QueryProjection
  public EndDatesByProjectId(Project project) {
    this.projectId = project.getId();
    this.endDate = project.getEndDate();
  }
}
