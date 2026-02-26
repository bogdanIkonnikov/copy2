package tbank.copy2.web.dto.verification;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class VerificationRequest {
    @Schema(description = "Email пользователя", example = "Jon")
    @Email(message = "Email адрес должен быть верного формата")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String email;

    @Schema(description = "Код подтверждения", example = "123456")
    @NotBlank(message = "Код подтверждения не может быть пустым")
    private String code;
}
