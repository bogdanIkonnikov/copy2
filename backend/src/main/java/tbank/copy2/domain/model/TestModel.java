package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.AccessMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class TestModel {
    private Long id;

    private String name;

    private Long userId;

    private String description;

    private List<QuestionModel> questions = new ArrayList<>();

    private List<TestAccessModel> accesses;

    private Boolean visible = true;

    private AccessMode accessMode = AccessMode.PRIVATE;

    private String shareToken;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
