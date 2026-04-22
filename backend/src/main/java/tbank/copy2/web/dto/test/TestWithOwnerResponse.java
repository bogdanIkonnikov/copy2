package tbank.copy2.web.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.AccessMode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Ответ с информацией о тесте")
public class TestWithOwnerResponse {
    @Schema(description = "Id теста", example = "1")
    private Long id;

    @Schema(description = "Название теста", example = "test1")
    private String name;

    @Schema(description = "Описание теста", example = "Simple test, that contains 10 questions")
    private String description;

    @Schema(description = "Дата последнего прохождения теста", example = "10.10.2025")
    private String lastUse;

    @Schema(description = "Количество вопросов в тесте", example = "10")
    private int questionsCount;

    @Schema(description = "Режим доступа к тесту", example = "PUBLIC")
    private AccessMode accessMode;

    @Schema(description = "Токен теста для создания ссылки на него", example = "21894tdoqs9012")
    private String shareToken;

    @Schema(description = "Является ли текущий пользователь владельцем теста", example = "true")
    private boolean isOwner;

    @Schema(description = "Количество правильных ответов в последнем прохождении", example = "14")
    private int progress;
}
