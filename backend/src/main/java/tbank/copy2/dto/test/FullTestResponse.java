package tbank.copy2.dto.test;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.entity.Test;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с информацией о тесте и вопросах в нем")
public class FullTestResponse {

    @Schema(description = "Название теста", example = "test1")
    private String name;

    @Schema(description = "Описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "Дата последнего прохождения теста", example = "10.10.2025")
    private String lastUse;

    @Schema(description = "Прогресс прохождения теста", example = "14")
    private int progress;

    @Schema(description = "Cписок вопросов")
    private List<QuestionResponse> questions;
}

