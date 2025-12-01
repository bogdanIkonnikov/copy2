package tbank.copy2.web.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для добавления нового вопроса")
public class AddQuestionRequest {
    @Schema(description = "Текст вопроса", example = "Что такое компьютер?")
    @NotBlank(message = "Question is required")
    private String content;

    @Schema(description = "id теста, к которому относится вопрос", example = "1")
    @NotNull(message = "testId cannot be null")
    @Positive(message = "testId must be positive")
    private Long testId;

    @Schema(description = "Тип вопроса", example = "CHOICE")
    @Pattern(regexp = "CHOICE, INPUT", message = "Invalid question type")
    private Type type;
}
