package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class TestSessionAnswerModel {
    private Long sessionId;
    private Long questionId;
    private List<Object> userAnswer;
}
