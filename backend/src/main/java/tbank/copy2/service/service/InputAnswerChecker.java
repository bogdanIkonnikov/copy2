package tbank.copy2.service.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.DAO.repository.AnswerModelRepository;
import tbank.copy2.web.dto.answer.CheckedAnswer;

import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class InputAnswerChecker implements AnswerChecker {
    @Autowired
    private AnswerModelRepository answerRepository;

    @Override
    public CheckedAnswer checkAnswer(Long questionId, List<Object> answers) {
        boolean isCorrect = false;
        String trueAnswer = answerRepository.findAllByQuestionId(questionId).get(0).getContent();
        String userAnswer = answers.get(0).toString();
        if (trueAnswer.equals(userAnswer)) {
            isCorrect = true;
        }
        List<Object> correctAnswer = new ArrayList<>();
        correctAnswer.add(trueAnswer);
        return new CheckedAnswer(correctAnswer, isCorrect);
    }
}
