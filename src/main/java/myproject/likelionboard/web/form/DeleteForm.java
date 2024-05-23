package myproject.likelionboard.web.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DeleteForm {
    @NotBlank
    String password;
}
