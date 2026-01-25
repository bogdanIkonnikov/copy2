package tbank.copy2.web.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Ответ с информацией о тестах с пагинацией")
public class TestPageResponse {
    private List<TestResponse> items;
    private int page;
    private int size;
    private int totalItems;
    private int totalPages;
}
