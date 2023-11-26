package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Common {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String description;

  private LocalDate createdDate;

  @Enumerated(EnumType.STRING)
  private ProjectType projectType;

  private String projectCreator;

  @OneToMany(mappedBy = "common")
  private List<Project> projects = new ArrayList<>();

  //연관관계 메서드
  public void addProject(Project project) {
    projects.add(project);
  }

  @Builder
  public Common(String title, String description, ProjectType projectType, String projectCreator) {
    this.title = title;
    this.description = description;
    this.createdDate = LocalDate.now();
    this.projectType = projectType;
    this.projectCreator = projectCreator;
  }
}
