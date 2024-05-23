package myproject.likelionboard.domain.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDto {

    @NotBlank
    @Max(value = 100)
    private String name;
    @NotBlank @Max(value = 255)
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private LocalDate updateDate;
    @NotBlank
    private String password;
}
