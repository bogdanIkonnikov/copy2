package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import tbank.copy2.common.enums.Type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
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
