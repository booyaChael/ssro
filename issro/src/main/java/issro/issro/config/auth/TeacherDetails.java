package issro.issro.config.auth;

import issro.issro.config.jwt.JwtProperties;
import issro.issro.domain.Teacher;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static issro.issro.config.jwt.JwtProperties.*;

@Getter
public class TeacherDetails implements UserDetails {

  private Teacher teacher;

  public TeacherDetails(Teacher teacher) {
    this.teacher = teacher;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(new SimpleGrantedAuthority(ROLE_TEACHER));
    return collect;
  }

  @Override
  public String getPassword() {
    return teacher.getPassword();
  }

  @Override
  public String getUsername() {
    return teacher.getEmail();
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
