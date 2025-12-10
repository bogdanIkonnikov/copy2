package tbank.copy2.service.service;

import org.springframework.stereotype.Component;
import tbank.copy2.service.model.CheckedAnswerModel;

import java.util.List;

@Component
public interface AnswerChecker {
    CheckedAnswerModel checkAnswer(Long questionId, List<Object> answers);
}
