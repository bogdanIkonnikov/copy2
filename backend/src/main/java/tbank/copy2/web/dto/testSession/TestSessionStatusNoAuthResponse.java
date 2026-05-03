package tbank.copy2.web.dto.testSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Расширенный статус тестовой сессии с токеном доступа")
public class TestSessionStatusNoAuthResponse extends TestSessionResponse {
    @Schema(description = "Токен для того, чтобы делиться результатом", example = "a1b2c3d4-e5f6-g7h8")
    private String shareToken;
}
