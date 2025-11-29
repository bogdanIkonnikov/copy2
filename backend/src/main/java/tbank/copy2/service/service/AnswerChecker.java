package tbank.copy2.service.service;

import org.springframework.stereotype.Component;
import tbank.copy2.web.dto.answer.CheckedAnswer;

import java.util.List;

@Component
public interface AnswerChecker {
    CheckedAnswer checkAnswer(Long questionId, List<Object> answers);
}
