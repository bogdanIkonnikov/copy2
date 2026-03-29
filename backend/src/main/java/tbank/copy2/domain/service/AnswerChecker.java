package tbank.copy2.domain.service;

import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.CheckedAnswerModel;

import java.util.List;

@Component
public interface AnswerChecker {
    CheckedAnswerModel checkAnswer(Long questionId, List<Object> answers);
}
