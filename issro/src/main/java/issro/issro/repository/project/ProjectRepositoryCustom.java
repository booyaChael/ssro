package issro.issro.repository.project;

import issro.issro.domain.Project;
import issro.issro.domain.Teacher;
import issro.issro.dto.project.ProjectByDeadlineMonth;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepositoryCustom {

  List<Project> getProjectByDate(Teacher teacher, LocalDate date);
  List<ProjectByDeadlineMonth> getProjectByDeadlineMonth(Teacher teacher, Integer month);

}
