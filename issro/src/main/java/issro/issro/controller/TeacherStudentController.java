package issro.issro.controller;

import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import issro.issro.dto.ResponseData;
import issro.issro.dto.student.StudentDTO;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static issro.issro.service.TeacherService.checkTeacherRole;

@RestController
@RequiredArgsConstructor
public class TeacherStudentController {

  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;

  @GetMapping("/teacherStudent")
  public ResponseData<?> getStudentByTeacher(Authentication authentication) {
    if (authentication == null || !checkTeacherRole(authentication.getAuthorities())) {
      return new ResponseData<>(HttpStatus.BAD_REQUEST, null);
    }
    Teacher teacher = teacherRepository.findByEmail(authentication.getName());
    List<Student> studentList = studentRepository.findStudentsByTeacher(teacher);
    List<StudentDTO> studentDTOList = studentList.stream()
            .map(student -> new StudentDTO(student.getId(), student.getName(), student.getNumber()))
            .toList();
    return new ResponseData<List<StudentDTO>>(HttpStatus.OK, studentDTOList);
  }
}
