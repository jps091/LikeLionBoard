package myproject.likelionboard.domain.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String title;
    @NotBlank
    private String password;
    @NotBlank
    private String content;
    //@NotNull
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
