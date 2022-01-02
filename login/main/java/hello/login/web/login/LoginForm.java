package hello.login.web.login;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

// 핵심 기능을 개발했다.

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

}
