package tbank.copy2.web.dto.answer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для изменение ответа на вопрос")
public class UpdateAnswerRequest {
    @Schema(description = "id ответа, который изменяет пользователь (null если добавляем ответ)", example = "1")
    @Positive
    private Long answerId;

    @Schema(description = "Содержимое ответа", example = "Это красный шарик")
    @NotEmpty
    private String content;

    @Schema(description = "Признак правильности ответа", example = "true")
    @NotNull
    private Boolean isCorrect;
}