package tbank.copy2.domain.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.domain.ai.SynonymCheckService;
import tbank.copy2.domain.repository.AnswerModelRepository;
import tbank.copy2.domain.model.CheckedAnswerModel;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
@NoArgsConstructor
public class InputAnswerChecker implements AnswerChecker {
    @Autowired
    private AnswerModelRepository answerRepository;
    @Autowired
    private SynonymCheckService checkService;

    @Override
    public CheckedAnswerModel checkAnswer(Long questionId, List<Object> answers) {
        String trueAnswer = answerRepository.findAllByQuestionId(questionId).get(0).getContent();
        String userAnswer = answers.get(0).toString();
        boolean isCorrect = checkService.check(trueAnswer, userAnswer);
        List<Object> correctAnswer = new ArrayList<>();
        correctAnswer.add(trueAnswer);
        return new CheckedAnswerModel(correctAnswer, isCorrect);
    }
}
