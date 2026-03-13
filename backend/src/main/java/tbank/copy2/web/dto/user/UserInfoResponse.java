package tbank.copy2.web.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserInfoResponse {
    @Schema(description = "Идентификатор пользователя", example = "1")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Vasiliy")
    private String username;
    @Schema(description = "Электронная почта пользователя", example = "example@mail.ru")
    private String email;
    @Schema(description = "URL фотографии пользователя", example = "https://example.com/photo.jpg")
    private String photoURL;
}
