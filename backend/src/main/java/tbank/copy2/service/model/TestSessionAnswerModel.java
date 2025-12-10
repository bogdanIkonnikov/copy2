package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@NoArgsConstructor
public class TestSessionAnswerModel {
    private Long sessionId;
    private Long questionId;
    private List<Object> userAnswer;
}
