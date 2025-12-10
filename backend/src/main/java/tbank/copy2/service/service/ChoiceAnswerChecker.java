package tbank.copy2.service.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.DAO.repository.AnswerModelRepository;
import tbank.copy2.service.model.CheckedAnswerModel;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class ChoiceAnswerChecker implements AnswerChecker {
    @Autowired
    private AnswerModelRepository repository;

    @Override
    public CheckedAnswerModel checkAnswer(Long questionId, List<Object> answers) {
        boolean isCorrect = false;
        Set<Long> trueAnswers =
                repository.findAllByQuestionId(questionId)
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
        return new CheckedAnswerModel(Arrays.asList(trueAnswers.toArray()), isCorrect);
    }
}
