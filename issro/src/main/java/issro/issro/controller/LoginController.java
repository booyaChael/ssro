package issro.issro.controller;

import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import issro.issro.dto.student.RequestStudentSaveDTO;
import issro.issro.dto.student.StudentSaveDTO;
import issro.issro.dto.teacher.TeacherSaveDto;
import issro.issro.error.DuplicationException;
import issro.issro.repository.TeacherRepository;
import issro.issro.service.StudentService;
import issro.issro.service.TeacherService;
import issro.issro.service.TeacherStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static issro.issro.service.TeacherService.checkTeacherRole;

@RestController
@RequiredArgsConstructor
public class LoginController {

  private final TeacherService teacherService;
  private final StudentService studentService;
  private final TeacherStudentService teacherStudentService;
  private final TeacherRepository teacherRepository;

  /**
   * 교사 회원 가입
   */

  @PostMapping("/teacher")
  public ResponseEntity<?> saveTeacher(@Validated @RequestBody TeacherSaveDto teacherSaveDto) {

    if (studentService.checkDuplication(teacherSaveDto.getEmail())) {
      throw new DuplicationException();
    }

    Teacher teacher = teacherService.save(teacherSaveDto);
    return new ResponseEntity<>("save success", HttpStatus.OK);
  }

  /**
   * 학생 회원 가입
   */
  @PostMapping("/student")
  public ResponseEntity<?> saveStudents(@Validated @RequestBody RequestStudentSaveDTO requestStudentSaveDTO, Authentication authentication) {

    if (authentication == null || !checkTeacherRole(authentication.getAuthorities()) || teacherRepository.findByNickname(requestStudentSaveDTO.getTeacherName()) == null) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    List<String> studentNameList = requestStudentSaveDTO.getStudentList().stream()
            .map(StudentSaveDTO::getUsername)
            .toList();

    if (teacherService.checkDuplication(studentNameList)) {
      throw new DuplicationException();
    }

    List<Student> savedStudentList = studentService.save(requestStudentSaveDTO.getStudentList());

    teacherStudentService.createTeacherStudentAndSave(requestStudentSaveDTO.getTeacherName(), savedStudentList);
    return new ResponseEntity<>("save success", HttpStatus.OK);
  }











}
