package issro.issro.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import issro.issro.config.auth.StudentDetails;
import issro.issro.config.auth.TeacherDetails;
import issro.issro.dto.UserLoginDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static issro.issro.config.jwt.JwtProperties.*;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      var om = new ObjectMapper();
      UserLoginDTO userLoginDTO = om.readValue(request.getInputStream(), UserLoginDTO.class);
      var authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
      Authentication authentication = authenticationManager.authenticate(authenticationToken);
      return authentication;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();

    boolean isTeacherRole = true;

    for (GrantedAuthority authority : authorities) {
      String role = authority.getAuthority();
      if (role.equals(ROLE_STUDENT)) {
        isTeacherRole = false;
        break;
      }
    }

    UserDetails userDetails = null;

    if (isTeacherRole) {
      userDetails = (TeacherDetails) authResult.getPrincipal();
    } else {
      userDetails = (StudentDetails) authResult.getPrincipal();
    }

    String token = JWT.create()
            .withSubject(userDetails.getUsername()) // 토큰 이름 -> 바꿀 수 있음.
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .withClaim("username", userDetails.getUsername())
            .withClaim("role", isTeacherRole ? ROLE_TEACHER : ROLE_STUDENT)
            .sign(Algorithm.HMAC512(SECRET));

    // json으로 하면 안되는가?
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }
}
