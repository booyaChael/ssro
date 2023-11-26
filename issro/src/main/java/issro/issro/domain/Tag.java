package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

  @Id
  @Column(name = "tag_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @Column(nullable = false)
  private String tagName;

  public void addTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public void addTagName(String tagName) {
    this.tagName = tagName;
  }

  // 생성 함수
  public static Tag createTag(String tagName, Teacher teacher) {
    Tag tag = new Tag();
    tag.addTagName(tagName);
    tag.addTeacher(teacher);
    return tag;
  }

}
