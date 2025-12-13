package tbank.copy2.web.dto.answer;

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
    @NotNull
    @Positive
    private Long answerId;

    @Schema(description = "Содержимое ответа", example = "Это красный шарик")
    @NotEmpty
    private String content;

    @Schema(description = "Признак правильности ответа", example = "true")
    @NotNull
    private Boolean isCorrect;
}
