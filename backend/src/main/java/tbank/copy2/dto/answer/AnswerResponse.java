package tbank.copy2.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ на вопрос")
public class AnswerResponse {

    @Schema(description = "Id ответа", example = "1")
    private Long id;

    @Schema(description = "Текст ответа", example = "Ответ 1")
    private String content;

    @Schema(description = "Флаг правильности ответа", example = "true")
    private Boolean isCorrect;
}