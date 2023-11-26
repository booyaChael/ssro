package issro.issro.config.securityconfig;

import issro.issro.config.jwt.JwtAuthenticationFilter;
import issro.issro.config.jwt.JwtAuthorizationFilter;
import issro.issro.repository.TeacherRepository;
import issro.issro.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsFilter corsFilter;
  private final TeacherRepository teacherRepository;
  private final StudentRepository studentRepository;

  @Bean
  public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> {
              session.sessionCreationPolicy(STATELESS);
            })
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(
                            new AntPathRequestMatcher("/teacher/**"),
                            new AntPathRequestMatcher("/student/**"),
//                            new AntPathRequestMatcher("/login/student"),
//                            new AntPathRequestMatcher("/login/teacher"),
                            new AntPathRequestMatcher("/tag/**"),
                            new AntPathRequestMatcher("/teacherStudent/**"),
                            new AntPathRequestMatcher("/todo/**")
                    )
                    .permitAll()
                    .anyRequest().authenticated()
            )
            .apply(new UserCustomFilter());

    return http.build();
  }

  public class UserCustomFilter extends AbstractHttpConfigurer<UserCustomFilter, HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) throws Exception {

      AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
      JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);
      jwtAuthenticationFilter.setFilterProcessesUrl("/login/user");

      http
              .addFilter(corsFilter)
              .addFilter(jwtAuthenticationFilter)
              .addFilter(new JwtAuthorizationFilter(authenticationManager, teacherRepository, studentRepository));
    }
  }
}
