package issro.issro.service;

import issro.issro.domain.*;
import issro.issro.repository.studentProject.StudentProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static issro.issro.domain.ProjectType.*;
import static issro.issro.domain.SubmitState.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentProjectService {

  private final StudentProjectRepository studentProjectRepository;

  @Transactional
  public void createStudentProjects(int maxCount, ProjectType projectType, List<Student> students, Project ... projects) {

    Arrays.stream(projects).forEach(project -> {

      List<StudentProject> studentProjects = students.stream().map(student -> {
                StudentProject studentProject = StudentProject.createStudentProject(project, student);
                studentProject.addSubmitState(NULL);
                studentProject.addCount(0);
                if (projectType.equals(ONE_TIME) || projectType.equals(REPEAT)) {
                  studentProject.addMaxCount(0);
                }
                if (projectType.equals(MULTIPLE_TIME)) {
                  studentProject.addMaxCount(maxCount);
                }
                return studentProject;
              })
              .toList();
      studentProjectRepository.saveAll(studentProjects);
    });
  }
}
