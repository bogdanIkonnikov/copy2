package tbank.copy2.web.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание новой тестовой сессии")
public class AddTestSessionRequest {

    @Schema(description = "Идентификатор теста", example = "1")
    @NotNull
    private Long testId;

    @Schema(description = "Идентификатор пользователя", example = "1")
    @NotNull
    private Long userId;

}
