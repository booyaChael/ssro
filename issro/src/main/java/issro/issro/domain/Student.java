package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "student_id")
  private Long id;

  //아이디
  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private int number;

  @OneToMany(mappedBy = "student")
  private List<TeacherStudent> studentTeachers = new ArrayList<>();

  @Builder
  public Student(String username, String name, String password, int number) {
    this.username = username;
    this.name = name;
    this.password = password;
    this.number = number;
  }
}
