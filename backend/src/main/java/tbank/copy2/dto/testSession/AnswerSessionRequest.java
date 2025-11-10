package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос с ответами на сессию")
public class AnswerSessionRequest {

    @Schema(description = "Идентификатор вопроса", example = "1")
    private Long questionId;

    @Schema(description = "Ответ пользователя в виде списка значений",
            type = "array", implementation = Object.class)
    private List<Object> userAnswer;
}
