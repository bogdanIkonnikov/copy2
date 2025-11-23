package tbank.copy2.web.dto.test;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для создания теста")
public class AddTestRequest {

    @Schema(description = "Название теста", example = "test1")
    @NotBlank
    @Size(min = 1, max =30)
    private String name;

    @Schema(description = "Описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "ID пользователя-владельца", example = "1")
    @NotNull
    private Long userId;
}