package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.answer.AnswerResponse;
import tbank.copy2.entity.Answer;
import tbank.copy2.repository.AnswerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public List<AnswerResponse> getAnswersByQuestionId(Long testId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(testId);
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : answers) {
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(answer.getId());
            answerResponse.setIsCorrect(answer.getIsCorrect());
            answerResponse.setContent(answer.getContent());
        }
        return answerResponses;
    }
}
