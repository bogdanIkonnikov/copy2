package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Статус тестовой сессии")
public class TestSessionStatusResponse {

    @Schema(description = "Идентификатор сессии", example = "1")
    private Long sessionId;

    @Schema(description = "Идентификатор теста", example = "1")
    private Long testId;

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long userId;

    @Schema(description = "Прогресс прохождения теста (кол-во вопросов)", example = "12")
    private Long progress;

    @Schema(description = "Общее количество вопросов в тесте", example = "40")
    private Long questionsCount;

    @Schema(description = "Время завершения сессии", example = "2023-10-27T10:30:00.123")
    private String finished_at;
}
