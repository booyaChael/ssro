package issro.issro.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import issro.issro.config.auth.StudentDetails;
import issro.issro.config.auth.TeacherDetails;
import issro.issro.domain.Student;
import issro.issro.domain.Teacher;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.student.StudentRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static issro.issro.config.jwt.JwtProperties.*;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TeacherRepository teacherRepository, StudentRepository studentRepository) {
    super(authenticationManager);
    this.teacherRepository = teacherRepository;
    this.studentRepository = studentRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    String jwtHeader = request.getHeader(HEADER_STRING);
    if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    String token = jwtHeader.replace(TOKEN_PREFIX, "");

    DecodedJWT verify = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token);
    String username = verify.getClaim("username").asString();
    String role = verify.getClaim("role").asString();

    if (role.equals(ROLE_TEACHER) && username != null) {
      Teacher teacher = teacherRepository.findByEmail(username);
      var teacherDetails = new TeacherDetails(teacher);
      Authentication authentication = new UsernamePasswordAuthenticationToken(teacherDetails, null, teacherDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else if (role.equals(ROLE_STUDENT) && username != null) {
      Student student = studentRepository.findByUsername(username);
      var studentDetails = new StudentDetails(student);
      Authentication authentication = new UsernamePasswordAuthenticationToken(studentDetails, null, studentDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    chain.doFilter(request, response);
  }
}
