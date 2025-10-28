package tbank.copy2.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.entity.Answer;
import tbank.copy2.enums.Type;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Вопрос с вариантами ответов")
public class QuestionResponse {

    @Schema(description = "Текст вопроса", example = "Какой цвет неба?")
    private String content;

    @Schema(description = "Тип вопроса", example = "CHOICE")
    private Type type;

    @Schema(description = "Список ответов на вопрос")
    private List<AnswerResponse> answers = new ArrayList<>();
}
