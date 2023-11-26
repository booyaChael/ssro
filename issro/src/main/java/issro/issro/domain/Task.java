package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "student_project_id")
  private StudentProject studentProject;

  private String title;
  private String content;
  private String fileURL;
  private LocalDate submittedDate;
  private int multipleTaskIdx;

  @Builder
  public Task(StudentProject studentProject, String title, String content, String fileURL, LocalDate submittedDate, int multipleTaskIdx) {
    this.studentProject = studentProject;
    this.title = title;
    this.content = content;
    this.fileURL = fileURL;
    this.submittedDate = submittedDate;
    this.multipleTaskIdx = multipleTaskIdx;
  }
}
