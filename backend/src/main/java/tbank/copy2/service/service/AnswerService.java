package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.web.dto.answer.AddAnswerRequest;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.web.mapper.AnswerMapper;
import tbank.copy2.repository.repository.AnswerRepository;
import tbank.copy2.repository.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerMapper answerMapper;

    public List<AnswerResponse> getAnswersByQuestionId(Long questionId) {
        List<Answer> answers = answerRepository.findAllByQuestionId(questionId);
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : answers) {
            answerResponses.add(answerMapper.toResponse(answer));
        }
        return answerResponses;
    }

    public AnswerResponse addAnswer(AddAnswerRequest addAnswerRequest) {
        Question question = questionRepository.findById(addAnswerRequest.getQuestionId()).orElse(null);
        Answer answer = answerMapper.toEntity(addAnswerRequest, question);
        answerRepository.save(answer);
        return answerMapper.toResponse(answer);
    }
}
