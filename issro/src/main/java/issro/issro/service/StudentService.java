package issro.issro.service;

import issro.issro.domain.Student;
import issro.issro.dto.student.StudentSaveDTO;
import issro.issro.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static issro.issro.config.jwt.JwtProperties.ROLE_TEACHER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;
  private final BCryptPasswordEncoder encoder;

  @Transactional
  public List<Student> save(List<StudentSaveDTO> studentSaveDTOList) {
    List<Student> studentsList = studentSaveDTOList.stream()
            .map(dto -> {
              Student student = Student.builder()
                      .username(dto.getUsername())
                      .password(encoder.encode(dto.getPassword()))
                      .name(dto.getName())
                      .number(dto.getNumber())
                      .build();
              studentRepository.save(student);
              return student;
            }).toList();
    return studentsList;
  }

  public boolean checkDuplication(String username) {
    Student student = studentRepository.findByUsername(username);
    return student != null;
  }

  public static boolean checkStudentRole(Collection<? extends GrantedAuthority> authorities) {
    boolean isTeacherRole = true;

    for (GrantedAuthority authority : authorities) {
      String role = authority.getAuthority();
      if (role.equals(ROLE_TEACHER)) {
        isTeacherRole = false;
        break;
      }
    }
    return isTeacherRole;
  }
}
