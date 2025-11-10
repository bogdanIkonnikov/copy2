package tbank.copy2.dto.test;

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

    @Schema(description = "Название теста", example = "test1", required = true)
    @NotBlank
    private String name;

    @Schema(description = "Описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "ID пользователя-владельца", example = "1", required = true)
    @NotNull
    private Long userId;
}