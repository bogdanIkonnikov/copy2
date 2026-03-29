package tbank.copy2.domain.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Override
    public CheckedAnswerModel checkAnswer(Long questionId, List<Object> answers) {
        boolean isCorrect = false;
        String trueAnswer = answerRepository.findAllByQuestionId(questionId).get(0).getContent();
        String userAnswer = answers.get(0).toString();
        if (trueAnswer.equals(userAnswer)) {
            isCorrect = true;
        }
        List<Object> correctAnswer = new ArrayList<>();
        correctAnswer.add(trueAnswer);
        return new CheckedAnswerModel(correctAnswer, isCorrect);
    }
}
