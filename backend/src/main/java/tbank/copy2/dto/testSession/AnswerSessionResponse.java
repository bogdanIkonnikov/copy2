package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Ответ на вопрос в сессии")
public class AnswerSessionResponse {

    @Schema(description = "Флаг, указывающий, что ответ сохранён", example = "true")
    private Boolean saved;

    @Schema(description = "Флаг, указывающий что ответ правильный", example = "false")
    private Boolean isCorrect;

    @Schema(description = "Список правильных ответов", type = "array", implementation = Object.class)
    private List<Object> correctAnswer;
}
