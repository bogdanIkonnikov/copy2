package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.answer.AnswerResponse;
import tbank.copy2.dto.question.AddQuestionRequest;
import tbank.copy2.dto.question.QuestionResponse;
import tbank.copy2.dto.question.QuestionLightResponse;
import tbank.copy2.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.entity.Answer;
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

    public List<QuestionWithAnswersResponse> getQuestionsByTestId(Long testId) {
        List<QuestionWithAnswersResponse> questionResponseList = new ArrayList<>();
        List<Question> questions = questionRepository.findAllByTestId(testId);
        for (Question question : questions) {
            QuestionWithAnswersResponse questionResponse = new QuestionWithAnswersResponse();
            questionResponse.setId(question.getId());
            questionResponse.setType(question.getType());
            questionResponse.setContent(question.getContent());
            List<Answer> answers = question.getAnswers();
            List<AnswerResponse> answerResponses = new ArrayList<>();
            for (Answer answer : answers) {
                AnswerResponse answerResponse = new AnswerResponse();
                answerResponse.setId(answer.getId());
                answerResponse.setContent(answer.getContent());
                answerResponse.setIsCorrect(answer.getIsCorrect());
                answerResponses.add(answerResponse);
            }
            questionResponse.setAnswers(answerResponses);
            questionResponseList.add(questionResponse);

        }
        return questionResponseList;
    }

    public QuestionResponse addQuestion(AddQuestionRequest addQuestionRequest) {
        Question question = new Question();
        question.setType(addQuestionRequest.getType());
        question.setContent(addQuestionRequest.getContent());
        question.setTest(testRepository.findById(addQuestionRequest.getTestId()).orElse(null));
        question = questionRepository.save(question);
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setType(question.getType());
        questionResponse.setContent(question.getContent());
        return questionResponse;
    }

    public List<QuestionLightResponse> getLightQuestionsByTestId(Long testId) {
        List<Question> questions = questionRepository.findAllByTestId(testId);
        List<QuestionLightResponse> questionResponseList = new ArrayList<>();
        for (Question question : questions) {
            QuestionLightResponse questionResponse = new QuestionLightResponse();
            questionResponse.setId(question.getId());
            questionResponse.setContent(question.getContent());
            questionResponseList.add(questionResponse);
        }
        return questionResponseList;
    }

    public QuestionWithAnswersResponse getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId).orElse(null);
        QuestionWithAnswersResponse questionResponse = new QuestionWithAnswersResponse();
        questionResponse.setId(question.getId());
        questionResponse.setType(question.getType());
        questionResponse.setContent(question.getContent());
        List<AnswerResponse> answerResponses = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            AnswerResponse answerResponse = new AnswerResponse();
            answerResponse.setId(answer.getId());
            answerResponse.setContent(answer.getContent());
            answerResponse.setIsCorrect(answer.getIsCorrect());
            answerResponses.add(answerResponse);
        }
        questionResponse.setAnswers(answerResponses);
        return questionResponse;
    }
}
