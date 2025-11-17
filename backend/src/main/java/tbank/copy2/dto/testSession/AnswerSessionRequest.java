package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Запрос с ответами на сессию")
public class AnswerSessionRequest {

    @Schema(description = "Идентификатор вопроса", example = "1")
    @NotNull(message = "questionId cannot be null")
    @Positive(message = "questionId must be positive")
    private Long questionId;

    @Schema(description = "Ответ пользователя в виде списка значений",
            type = "array", implementation = Object.class)
    @NotEmpty(message = "userAnswer is required")
    private List<Object> userAnswer;
}
