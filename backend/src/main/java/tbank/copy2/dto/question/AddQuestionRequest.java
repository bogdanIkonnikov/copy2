package tbank.copy2.dto.question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для добавления нового вопроса")
public class AddQuestionRequest {
    @Schema(description = "Текст вопроса", example = "Что такое компьютер?")
    private String content;

    @Schema(description = "id теста, к которому относится вопрос", example = "1")
    private Long testId;

    @Schema(description = "Тип вопроса", example = "CHOICE")
    private Type type;
}
