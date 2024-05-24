package myproject.likelionboard.domain.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "제목은 필수 입력 사항 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입력 사항 입니다.")
    private String content;
    private LocalDateTime createDate;
    @NotBlank(message = "수정하려면 비밀번호는 필수 값 입니다.")
    private String password;
}
