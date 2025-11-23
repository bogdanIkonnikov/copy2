package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.web.dto.question.AddQuestionRequest;
import tbank.copy2.web.dto.question.QuestionResponse;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.web.mapper.AnswerMapper;
import tbank.copy2.web.mapper.QuestionMapper;
import tbank.copy2.repository.repository.QuestionRepository;
import tbank.copy2.repository.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;

    public List<QuestionWithAnswersResponse> getQuestionsByTestId(Long testId) {
        List<QuestionWithAnswersResponse> questionResponseList = new ArrayList<>();
        List<Question> questions = questionRepository.findAllByTestId(testId);
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers();
            List<AnswerResponse> answerResponses = new ArrayList<>();
            for (Answer answer : answers) {
                AnswerResponse answerResponse = answerMapper.toResponse(answer);
                answerResponses.add(answerResponse);
            }
            QuestionWithAnswersResponse response = questionMapper.toResponseWithAnswers(question, answerResponses);
            questionResponseList.add(response);

        }
        return questionResponseList;
    }

    public QuestionResponse addQuestion(AddQuestionRequest addQuestionRequest) {
        Test test = testRepository.findById(addQuestionRequest.getTestId()).orElse(null);
        Question question = questionMapper.toQuestion(addQuestionRequest, test);
        questionRepository.save(question);
        return questionMapper.toResponse(question);
    }

    public List<QuestionLightResponse> getLightQuestionsByTestId(Long testId) {
        List<Question> questions = questionRepository.findAllByTestId(testId);
        List<QuestionLightResponse> questionResponseList = new ArrayList<>();
        for (Question question : questions) {
            QuestionLightResponse questionResponse = questionMapper.toLightResponse(question);
            questionResponseList.add(questionResponse);
        }
        return questionResponseList;
    }

    public QuestionWithAnswersResponse getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            AnswerResponse answerResponse = answerMapper.toResponse(answer);
            answerResponses.add(answerResponse);
        }
        return questionMapper.toResponseWithAnswers(question, answerResponses);
    }
}
