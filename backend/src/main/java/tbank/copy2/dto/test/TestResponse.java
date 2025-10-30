package tbank.copy2.dto.test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import tbank.copy2.entity.Test;

@Data
@AllArgsConstructor
@Schema(description = "Ответ с информацией о тесте")
public class TestResponse {
    @Schema(description = "Id теста", example = "1")
    private Long id;

    @Schema(description = "Название теста", example = "test1")
    private String name;

    @Schema(description = "Описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "Количество вопросов в тесте", example = "10")
    private int questionsCount;

    @Schema(description = "Прогресс прохождения теста", example = "14")
    private int progress;

    public TestResponse(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.description = test.getDescription();
        this.questionsCount = test.getQuestions().size();
        this.progress = 14; // Заглушка, заменить логикой
    }
}

