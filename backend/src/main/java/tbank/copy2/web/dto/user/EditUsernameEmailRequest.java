package tbank.copy2.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на изменение имени и почты пользователя")
public class EditUsernameEmailRequest {
    @Schema(description = "Имя пользователя", example = "Jon")
    @Size(min = 2, max = 50, message = "Имя пользователя должно содержать от 2 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String username;

    @Schema(description = "Email пользователя", example = "Jon@mail.ru")
    @Email(message = "Email адрес должен быть верного формата")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String email;
}
