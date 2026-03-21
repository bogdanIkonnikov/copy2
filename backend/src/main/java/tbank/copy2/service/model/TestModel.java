package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    private Boolean visible = true;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
