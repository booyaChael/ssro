package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "studentProject")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentProject {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_project_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @Enumerated(EnumType.STRING)
  private SubmitState submitState;

  private int maxCount;
  private int count;

  public void addMaxCount(int maxCount) {
    this.maxCount = maxCount;
  }

  public void addCount(int count) {
    this.count = count;
  }

  public void addSubmitState(SubmitState submitState) {
    this.submitState = submitState;
  }

  private void addProject(Project project) {
    this.project = project;
  }

  private void addStudent(Student student) {
    this.student = student;
  }

  public static StudentProject createStudentProject(Project project, Student student) {
    StudentProject studentProject = new StudentProject();
    studentProject.addProject(project);
    studentProject.addStudent(student);
    return studentProject;
  }

}
