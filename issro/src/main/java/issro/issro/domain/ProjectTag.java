package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "project_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTag {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "project_tag_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  private void addProject(Project project) {
    this.project = project;
  }

  private void addTag(Tag tag) {
    this.tag = tag;
  }

  public static ProjectTag createProjectTag(Project project, Tag tag) {
    ProjectTag projectTag = new ProjectTag();
    projectTag.addProject(project);
    projectTag.addTag(tag);
    project.addProjectTag(projectTag);
    return projectTag;
  }

}
