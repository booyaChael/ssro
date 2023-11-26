package issro.issro.service;

import issro.issro.domain.Common;
import issro.issro.domain.Project;
import issro.issro.domain.Teacher;
import issro.issro.dto.project.DailyProjectListByTeacherDTO;
import issro.issro.dto.project.ProjectByDeadlineMonth;
import issro.issro.dto.project.RequestProjectCreateDTO;
import issro.issro.repository.project.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

  private final ProjectRepository projectRepository;

  @Transactional
  public Project createOneTimeMultiTimeProjectAndSaveByProjectByTeacher(RequestProjectCreateDTO projectDTO, Teacher teacher, Common common) {
      Project project = Project.builder()
              .teacher(teacher)
              .startDate(projectDTO.getStartDate())
              .endDate(projectDTO.getEndDate())
              .shouldSubmitNum(projectDTO.getStudentIds().size())
              .submittedNum(0)
              .build();
      project.addCommon(common);

    return projectRepository.save(project);
  }

  @Transactional
  public List<Project> createRepeatProjectAndSaveByProjectByTeacher(RequestProjectCreateDTO projectDTO, Teacher teacher, Common common) {
    LocalDate startDate = projectDTO.getStartDate();
    LocalDate endDate = projectDTO.getEndDate();
    List<Integer> week = projectDTO.getWeek();
    int releaseDate = projectDTO.getReleaseDate();

    List<Project> projects = new ArrayList<>();

    int dayOfWeek = startDate.getDayOfWeek().getValue();

    for (int plusDay = 0; plusDay < 7; plusDay++) {
      LocalDate today = startDate.plusDays(plusDay);
      if ((today.isBefore(endDate) || today.isEqual(endDate)) && week.contains((dayOfWeek + plusDay) % 7)) {

        LocalDate nextDay = today;
        while (nextDay.isBefore(endDate) || nextDay.isEqual(endDate)) {

          Project project = Project.builder()
                  .teacher(teacher)
                  .startDate(nextDay.minusDays(releaseDate))
                  .endDate(nextDay)
                  .shouldSubmitNum(projectDTO.getStudentIds().size())
                  .submittedNum(0)
                  .build();
          project.addCommon(common);

          projects.add(project);

          nextDay = nextDay.plusDays(7);
        }
      }
    }
    projectRepository.saveAll(projects);
    return projects;
  }

  public List<DailyProjectListByTeacherDTO> getDailyProjectsByTeacher(Teacher teacher, LocalDate date) {
    List<Project> projectList = projectRepository.getProjectByDate(teacher, date);
    return projectList.stream()
            .map(DailyProjectListByTeacherDTO::new)
            .toList();
  }

  public List<ProjectByDeadlineMonth> getTodoByDeadlineMonth(Teacher teacher, Integer month) {
    return projectRepository.getProjectByDeadlineMonth(teacher, month);
  }


}
