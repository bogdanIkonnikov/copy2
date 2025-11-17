package tbank.copy2.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание новой тестовой сессии")
public class AddTestSessionRequest {

    @Schema(description = "Идентификатор теста", example = "1")
    @NotNull(message = "testId cannot be null")
    @Positive(message = "testId must be positive")
    private Long testId;

    @Schema(description = "Идентификатор пользователя", example = "1")
    @NotNull(message = "userId cannot be null")
    @Positive(message = "userId must be positive")
    private Long userId;

}
