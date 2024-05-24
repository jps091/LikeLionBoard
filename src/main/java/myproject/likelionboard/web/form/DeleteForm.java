package myproject.likelionboard.web.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class DeleteForm {
    @NotBlank(message = "삭제하려면 비밀번호는 필수 값 입니다.")
    String password;
}
