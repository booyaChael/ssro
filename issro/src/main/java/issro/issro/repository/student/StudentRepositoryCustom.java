package issro.issro.repository.student;

import issro.issro.domain.Student;
import issro.issro.domain.Teacher;

import java.util.List;

public interface StudentRepositoryCustom {
  List<Student> findStudentsByTeacher(Teacher teacher);
}
