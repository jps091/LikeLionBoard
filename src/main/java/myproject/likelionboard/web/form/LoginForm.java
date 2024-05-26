package myproject.likelionboard.web.form;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    @NotBlank(message = "가입 ID를 입력 하세요.")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력 하세요.")
    private String password;
}
