package tbank.copy2.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Email пользователя", example = "Jon")
    @Email(message = "Email адрес должен быть верного формата")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String email;

    @Schema(description = "Пароль", example = "12345")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @NotBlank(message = "Пароль не может быть пустыми")
    private String password;
}
