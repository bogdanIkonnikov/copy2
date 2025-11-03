package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.question.AddQuestionRequest;
import tbank.copy2.dto.question.QuestionResponse;
import tbank.copy2.entity.Question;
import tbank.copy2.repository.QuestionRepository;
import tbank.copy2.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;

    public List<QuestionResponse> getQuestionsByTestId(Long testId) {
        List<QuestionResponse> questionResponseList = new ArrayList<>();
        List<Question> questions = questionRepository.findAllByTestId(testId);
        for (Question question : questions) {
            QuestionResponse questionResponse = new QuestionResponse();
            questionResponse.setId(question.getId());
            questionResponse.setType(question.getType());
            questionResponse.setContent(question.getContent());
            questionResponseList.add(questionResponse);
        }
        return questionResponseList;
    }


}
