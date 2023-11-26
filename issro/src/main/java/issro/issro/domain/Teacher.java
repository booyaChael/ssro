package issro.issro.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table @Getter
@NoArgsConstructor(access = PROTECTED)
public class Teacher {

  @Builder
  public Teacher(String email, String password, String nickname, String avatarSgv) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.avatarSgv = avatarSgv;
  }

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "teacher_id")
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(unique = true, nullable = false)
  private String nickname;

  private String avatarSgv;

  @OneToMany(mappedBy = "teacher")
  private List<TeacherStudent> teacherStudents = new ArrayList<>();
}
