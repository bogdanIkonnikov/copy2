package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class QuestionModel {

    private Long id;

    private Long testId;

    private List<AnswerModel> answerModels = new ArrayList<>();

    private String content;

    private Type type;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
