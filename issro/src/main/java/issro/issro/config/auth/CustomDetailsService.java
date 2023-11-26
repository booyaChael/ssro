package issro.issro.config.auth;

import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailsService implements UserDetailsService {

  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Teacher teacher = teacherRepository.findByEmail(username);
    if (teacher != null) {
      return new TeacherDetails(teacher);
    }
    Student student = studentRepository.findByUsername(username);
    if (student != null) {
      return new StudentDetails(student);
    }
    return null;
  }
}
