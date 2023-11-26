package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "project_id")
  private Long id;

  private LocalDate startDate;
  private LocalDate endDate;
  private int shouldSubmitNum;
  private int submittedNum;

  @OneToMany(mappedBy = "project")
  private List<ProjectTag> projectTags = new ArrayList<>();

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name="common_id")
  private Common common;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name="teacher_id")
  private Teacher teacher;

  //연관관계 메서드
  public void addProjectTag(ProjectTag projectTag) {
    this.projectTags.add(projectTag);
  }

  public void addCommon(Common common) {
    this.common = common;
    common.addProject(this);
  }
  //생성 메서드
  @Builder
  public Project(LocalDate startDate, LocalDate endDate, int shouldSubmitNum, int submittedNum, Teacher teacher) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.shouldSubmitNum = shouldSubmitNum;
    this.submittedNum = submittedNum;
    this.teacher = teacher;
  }
}
