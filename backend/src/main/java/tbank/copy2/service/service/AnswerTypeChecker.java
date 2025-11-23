package tbank.copy2.service.service;
import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.Type;
import tbank.copy2.repository.repository.QuestionRepository;

import java.util.Map;

@Component
public class AnswerTypeChecker {
    private QuestionRepository questionRepository;
    private AnswerChecker ChoiceAnswerChecker;
    private AnswerChecker InputAnswerChecker;

    private Map<Type, AnswerChecker> checkers = Map.of(
            Type.CHOICE, ChoiceAnswerChecker,
            Type.INPUT, InputAnswerChecker
    );

    public AnswerChecker check(Long questionId) {
        Type type = questionRepository.getQuestionById(questionId).getType();
        return checkers.get(type);
    }


}
