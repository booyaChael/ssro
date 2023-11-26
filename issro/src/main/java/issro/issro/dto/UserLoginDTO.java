package issro.issro.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

  private String username;
  private String password;
  private String role;

}
