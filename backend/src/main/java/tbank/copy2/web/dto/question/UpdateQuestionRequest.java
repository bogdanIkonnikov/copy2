package tbank.copy2.web.dto.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.Type;
import tbank.copy2.web.dto.answer.UpdateAnswerRequest;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для добавления нового вопроса")
public class UpdateQuestionRequest {
    @Schema(description = "id вопроса, который изменяет пользователь (null если добавляем вопрос)", example = "1")
    @Positive
    private Long questionId;

    @Schema(description = "Текст вопроса", example = "Что такое компьютер?")
    @NotBlank
    private String content;

    @Schema(description = "Тип вопроса", example = "CHOICE")
    @NotNull
    private Type type;

    @Schema(description = "Список ответов на вопрос")
    private List<UpdateAnswerRequest> answers = new ArrayList<>();
}
