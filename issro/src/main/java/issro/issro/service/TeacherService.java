package issro.issro.service;

import issro.issro.domain.Teacher;
import issro.issro.dto.teacher.TeacherSaveDto;
import issro.issro.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static issro.issro.config.jwt.JwtProperties.ROLE_STUDENT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeacherService {

  private final TeacherRepository teacherRepository;
  private final BCryptPasswordEncoder encoder;

  @Transactional
  public Teacher save(TeacherSaveDto teacherSaveDto) {
//    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    Teacher teacher = Teacher.builder()
            .email(teacherSaveDto.getEmail())
            .password(encoder.encode(teacherSaveDto.getPassword()))
            .nickname(teacherSaveDto.getNickname())
            .avatarSgv(teacherSaveDto.getAvatarSgv())
            .build();
    teacherRepository.save(teacher);
    return teacher;
  }

  public boolean checkDuplication(List<String> studentNameList) {
    boolean duplication = false;
    for (String studentName : studentNameList) {
      Teacher teacher = teacherRepository.findByEmail(studentName);
      if (teacher != null) {
        duplication = true;
        break;
      }
    }
    return duplication;
  }

  public static boolean checkTeacherRole(Collection<? extends GrantedAuthority> authorities) {
    boolean isTeacherRole = true;

    for (GrantedAuthority authority : authorities) {
      String role = authority.getAuthority();
      if (role.equals(ROLE_STUDENT)) {
        isTeacherRole = false;
        break;
      }
    }
    return isTeacherRole;
  }
}
