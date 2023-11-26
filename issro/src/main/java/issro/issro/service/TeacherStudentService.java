package issro.issro.service;

import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import issro.issro.domain.TeacherStudent;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.TeacherStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherStudentService {

  private final TeacherRepository teacherRepository;
  private final TeacherStudentRepository teacherStudentRepository;

  @Transactional
  public void createTeacherStudentAndSave(String teacherName, List<Student> savedStudentList) {
    Teacher teacher = teacherRepository.findByNickname(teacherName);
    List<TeacherStudent> teacherStudentList = savedStudentList.stream()
            .map(student -> TeacherStudent.createTeacherStudent(teacher, student))
            .toList();
    teacherStudentRepository.saveAll(teacherStudentList);
  }
}
