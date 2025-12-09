package tbank.copy2.service.service;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.DAO.repository.QuestionModelRepository;
import tbank.copy2.common.enums.Type;

import java.util.Map;

@Component
@NoArgsConstructor
public class AnswerTypeChecker {
    private Map<Type, AnswerChecker> checkers;
    @Autowired
    private QuestionModelRepository questionRepository;

    @Autowired
    public AnswerTypeChecker(ChoiceAnswerChecker choiceAnswerChecker,
                             InputAnswerChecker inputAnswerChecker) {
        this.checkers = Map.of(
                Type.CHOICE, choiceAnswerChecker,
                Type.INPUT, inputAnswerChecker
        );
    }

    public AnswerChecker check(Long questionId) {
        Type type = questionRepository.findById(questionId).getType();
        return checkers.get(type);
    }


}
