package tbank.copy2.service.service;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.Type;
import tbank.copy2.repository.repository.QuestionRepository;

import java.util.Map;

@Component
public class AnswerTypeChecker {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiceAnswerChecker ChoiceAnswerChecker;
    @Autowired
    private InputAnswerChecker InputAnswerChecker;

    private Map<Type, AnswerChecker> checkers;

    @PostConstruct
    public void init() {
        this.checkers = Map.of(
                Type.CHOICE, ChoiceAnswerChecker,
                Type.INPUT, InputAnswerChecker
        );
    }

    public AnswerChecker check(Long questionId) {
        Type type = questionRepository.getQuestionById(questionId).getType();
        return checkers.get(type);
    }


}
