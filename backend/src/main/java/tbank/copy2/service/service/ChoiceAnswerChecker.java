package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.repository.AnswerRepository;
import tbank.copy2.web.dto.answer.CheckedAnswer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ChoiceAnswerChecker implements AnswerChecker {
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public CheckedAnswer checkAnswer(Long questionId, List<Object> answers) {
        boolean isCorrect = false;
        Set<Long> trueAnswers =
                answerRepository.findAllByQuestionId(questionId)
                        .stream()
                        .filter(answer -> Boolean.TRUE.equals(answer.getIsCorrect()))
                        .map(answer -> answer.getId())
                        .collect(Collectors.toSet());
        Set<Long> userAnswers = answers
                .stream()
                .map(o -> Long.parseLong(String.valueOf(o)))
                .collect(Collectors.toSet());
        if (trueAnswers.equals(userAnswers)) {
            isCorrect = true;
        }
        return new CheckedAnswer(answers, isCorrect);
    }
}
