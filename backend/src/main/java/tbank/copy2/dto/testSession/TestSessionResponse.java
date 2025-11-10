package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Ответ с информацией о тестовой сессии")
public class TestSessionResponse {

    @Schema(description = "Идентификатор сессии", example = "1")
    private Long sessionId;

    @Schema(description = "Идентификатор теста", example = "1")
    private Long testId;

    @Schema(description = "Название теста", example = "Тест по тестированию")
    private String testName;

    @Schema(description = "Время начала сессии", example = "2023-10-27T10:30:00.123")
    private String started_at;
}
