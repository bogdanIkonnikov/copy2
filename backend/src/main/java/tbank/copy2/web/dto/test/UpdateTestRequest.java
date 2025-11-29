package tbank.copy2.web.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.web.dto.question.UpdateQuestionRequest;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос для изменения теста")
public class UpdateTestRequest {
    @Schema(description = "Новое название теста", example = "test1")
    @NotBlank
    @Size(min = 1, max =30)
    private String name;

    @Schema(description = "Новое описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "Список вопросов в тесте")
    private List<UpdateQuestionRequest> questions = new ArrayList<>();
}
