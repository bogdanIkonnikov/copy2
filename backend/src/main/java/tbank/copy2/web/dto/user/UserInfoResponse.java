package tbank.copy2.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.Role;

@NoArgsConstructor
@Data
public class UserInfoResponse {
    @Schema(description = "Имя пользователя", example = "Vasiliy")
    private String username;
    @Schema(description = "Хэш пароля", example = "$2a$10$20BbfxP3l2YgdkqAYBruy.Hta93gp.DEdiyWc1ZlBvlWQ/35xvA5C")
    private String password;
    @Schema(description = "Электронная почта пользователя", example = "example@mail.ru")
    private String email;
    @Schema(description = "URL фотографии пользователя", example = "https://example.com/photo.jpg")
    private String photoURL;
    @Schema(description = "Роль пользователя", example = "USER")
    private Role role;
}
