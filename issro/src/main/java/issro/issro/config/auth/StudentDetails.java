package issro.issro.config.auth;

import issro.issro.config.jwt.JwtProperties;
import issro.issro.domain.Student;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static issro.issro.config.jwt.JwtProperties.*;

@Getter
public class StudentDetails implements UserDetails {

  private Student student;

  public StudentDetails(Student student) {
    this.student = student;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(new SimpleGrantedAuthority(ROLE_STUDENT));
    return collect;
  }

  @Override
  public String getPassword() {
    return student.getPassword();
  }

  @Override
  public String getUsername() {
    return student.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
