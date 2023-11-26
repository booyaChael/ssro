package issro.issro.dto.project;

import issro.issro.domain.Project;
import issro.issro.domain.ProjectType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DailyProjectListByTeacherDTO {

  private Long projectId;
  private LocalDate startDate;
  private LocalDate endDate;
  private int shouldSubmitNum;
  private int submittedNum;

  private String title;
  private String projectCreator;
  private ProjectType projectType;

  private List<String> tags;

  public DailyProjectListByTeacherDTO(Project project) {
    this.projectId = project.getId();
    this.startDate = project.getStartDate();
    this.endDate = project.getEndDate();
    this.shouldSubmitNum = project.getShouldSubmitNum();
    this.submittedNum = project.getSubmittedNum();

    this.title = project.getCommon().getTitle();
    this.projectCreator = project.getCommon().getProjectCreator();
    this.projectType = project.getCommon().getProjectType();

    this.tags = project.getProjectTags().stream()
            .map(todoTag -> todoTag.getTag().getTagName())
            .toList();
  }
}
