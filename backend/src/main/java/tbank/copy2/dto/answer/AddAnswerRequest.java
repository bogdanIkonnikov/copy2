package tbank.copy2.dto.answer;

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
    private Long questionId;

    @Schema(description = "Содержимое ответа", example = "Это красный шарик")
    private String content;

    @Schema(description = "Признак правильности ответа", example = "true")
    private Boolean isCorrect;
}
