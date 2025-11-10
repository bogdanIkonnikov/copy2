package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание новой тестовой сессии")
public class AddTestSessionRequest {

    @Schema(description = "Идентификатор теста", example = "1")
    private Long testId;

    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long userId;

}
