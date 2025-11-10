package tbank.copy2.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Вопрос")
public class QuestionLightResponse {
    @Schema(description = "Id вопроса", example = "1")
    private Long id;

    @Schema(description = "Текст вопроса", example = "Какой цвет неба?")
    private String content;
}