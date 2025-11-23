package tbank.copy2.service.service;

import tbank.copy2.web.dto.answer.CheckedAnswer;

import java.util.List;

public interface AnswerChecker {
    CheckedAnswer checkAnswer(Long questionId, List<Object> answers);
}
