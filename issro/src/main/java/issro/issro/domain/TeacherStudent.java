package issro.issro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "teacher_student")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherStudent {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "teacher_student_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "teacher_id")
  private Teacher teacher;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "student_id")
  private Student student;

  // 연관관계 메서드

  public void addStudent(Student student) {
    this.student = student;
    student.getStudentTeachers().add(this);
  }

  public void addTeacher(Teacher teacher) {
    this.teacher = teacher;
    teacher.getTeacherStudents().add(this);
  }

  // 생성 메서드
  public static TeacherStudent createTeacherStudent(Teacher teacher, Student student) {
    var teacherStudent = new TeacherStudent();
    teacherStudent.addTeacher(teacher);
    teacherStudent.addStudent(student);
    return teacherStudent;
  }

}
