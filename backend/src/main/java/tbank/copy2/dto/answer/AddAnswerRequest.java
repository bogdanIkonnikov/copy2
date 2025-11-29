package tbank.copy2.dto.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для добавления ответа на вопрос")
public class AddAnswerRequest {
    @Schema(description = "id вопроса, к которому добавляется ответ", example = "1")
    @NotNull(message = "Id сannot be null")
    @Positive(message = "Id must be positive")
    private Long questionId;

    @Schema(description = "Содержимое ответа", example = "Это красный шарик")
    @NotEmpty(message = "Answer is required")
    private String content;

    @Schema(description = "Признак правильности ответа", example = "true")
    @NotNull(message = "isCorrect cannot be null")
    private Boolean isCorrect;
}
