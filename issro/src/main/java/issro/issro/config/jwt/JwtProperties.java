package issro.issro.config.jwt;

public interface JwtProperties {
  String SECRET = "issro";
  int EXPIRATION_TIME = 60000 * 100;
  String TOKEN_PREFIX = "Bearer "; // 반드시 한 칸 띄어야 한다.
  String HEADER_STRING = "Authorization";
  String ROLE_TEACHER = "teacher";
  String ROLE_STUDENT = "student";
}
