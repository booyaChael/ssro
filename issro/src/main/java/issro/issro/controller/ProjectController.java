package issro.issro.controller;

import issro.issro.domain.*;
import issro.issro.dto.IdDTO;
import issro.issro.dto.LocalDateDTO;
import issro.issro.dto.ResponseData;
import issro.issro.dto.project.DailyProjectListByTeacherDTO;
import issro.issro.dto.project.EndDatesByProjectId;
import issro.issro.dto.project.ProjectByDeadlineMonth;
import issro.issro.dto.project.RequestProjectCreateDTO;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.common.CommonRepository;
import issro.issro.repository.project.ProjectRepository;
import issro.issro.repository.student.StudentRepository;
import issro.issro.service.ProjectService;
import issro.issro.service.ProjectTagService;
import issro.issro.service.StudentProjectService;
import issro.issro.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static issro.issro.domain.ProjectType.*;
import static issro.issro.service.StudentService.checkStudentRole;
import static issro.issro.service.TeacherService.checkTeacherRole;

@RestController
@RequiredArgsConstructor
public class ProjectController {

  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;
  private final CommonRepository commonRepository;
  private final ProjectRepository projectRepository;
  private final ProjectTagService projectTagService;
  private final TagService tagService;
  private final ProjectService projectService;
  private final StudentProjectService studentProjectService;

  @PostMapping("/project")
  public ResponseData<?> addProject(@RequestBody RequestProjectCreateDTO projectDTO, Authentication authentication) {
    if (authentication == null) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }

    if (checkTeacherRole(authentication.getAuthorities())) {
      Teacher teacher = teacherRepository.findByEmail(authentication.getName());

      Common common = Common.builder()
              .title(projectDTO.getTitle())
              .description(projectDTO.getDescription())
              .projectType(projectDTO.getProjectType())
              .projectCreator(projectDTO.getProjectCreator())
              .build();
      commonRepository.save(common);

      ProjectType projectType = projectDTO.getProjectType();
      int maxCount = projectDTO.getMaxCount();

      if (projectType.equals(ONE_TIME) || projectType.equals(MULTIPLE_TIME)) {

        Project project = projectService.createOneTimeMultiTimeProjectAndSaveByProjectByTeacher(projectDTO, teacher, common);
        List<Tag> tags = tagService.createTagsByProjectByTeacherDTO(projectDTO, teacher);
        projectTagService.createProjectTagAndSave(tags, project);
        List<Student> students = studentRepository.findAllById(projectDTO.getStudentIds());
        studentProjectService.createStudentProjects(maxCount, projectType, students, project);
      }

      else if (projectType.equals(REPEAT)) {

        List<Project> projects = projectService.createRepeatProjectAndSaveByProjectByTeacher(projectDTO, teacher, common);

        List<Tag> tags = tagService.createTagsByProjectByTeacherDTO(projectDTO, teacher);
        projects.forEach(project -> projectTagService.createProjectTagAndSave(tags, project));
        List<Student> students = studentRepository.findAllById(projectDTO.getStudentIds());
        studentProjectService.createStudentProjects(maxCount, projectType, students, projects.toArray(Project[]::new));
      }
    }

    if (checkStudentRole(authentication.getAuthorities())) {
      /**
       * 학생이 만드는 프로젝트는 아직 구현 안 함. 논의 필요.
       */
    }

    return new ResponseData<>(HttpStatus.OK, Map.of("message", "성공적으로 저장되었습니다."));
  }

  /**
   * 여기서부터 새로 만드시면 됩니다.
   */

  @GetMapping("/project/daily-project")
  public ResponseData<?> getDailyProjectByTeacher(@ModelAttribute LocalDateDTO date, Authentication authentication) {
    if (authentication == null || !checkTeacherRole(authentication.getAuthorities())) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }

    Teacher teacher = teacherRepository.findByEmail(authentication.getName());
    List<DailyProjectListByTeacherDTO> dailyProjectListDTO = projectService.getDailyProjectsByTeacher(teacher, date.getDate());

    if (dailyProjectListDTO.isEmpty()) {
      return new ResponseData<>(HttpStatus.OK, Map.of("message", "해당하는 과제가 없습니다."));
    }

    return new ResponseData<Map<String, List<DailyProjectListByTeacherDTO>>>(HttpStatus.OK, Map.of("projects", dailyProjectListDTO));
  }

  @GetMapping("/project/deadline")
  public ResponseData<?> getProjectDeadlineFromMonth(@ModelAttribute LocalDateDTO date, Authentication authentication) {
    if (authentication == null || !checkTeacherRole(authentication.getAuthorities())) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }

    Teacher teacher = teacherRepository.findByEmail(authentication.getName());
    List<ProjectByDeadlineMonth> projectByDeadlineMonthList = projectService.getTodoByDeadlineMonth(teacher, date.getMonth());

    if (projectByDeadlineMonthList.isEmpty()) {
      return new ResponseData<>(HttpStatus.OK, Map.of("message", "해당하는 과제가 없습니다."));
    }

    return new ResponseData<Map<String, List<ProjectByDeadlineMonth>>>(HttpStatus.OK, Map.of("deadline", projectByDeadlineMonthList));
  }

  @GetMapping("/project/end-dates")
  public  ResponseData<?> getProjectEndDatesFromProjectId(@ModelAttribute IdDTO projectId, Authentication authentication) {
    if (authentication == null || !checkTeacherRole(authentication.getAuthorities())) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }

    // 예외처리 넘나 어렵네요 대충 이렇게만 했슴다
    Project project = projectRepository.findById(projectId.getProjectId()).orElseThrow(() -> new IllegalArgumentException("project doesn't exist"));

    List<EndDatesByProjectId> endDatesByProjectId= project.getCommon().getProjects().stream()
            .map(EndDatesByProjectId::new)
            .toList();

    return new ResponseData<Map<String, List<EndDatesByProjectId>>>(HttpStatus.OK, Map.of("endDates", endDatesByProjectId));
  }

}
