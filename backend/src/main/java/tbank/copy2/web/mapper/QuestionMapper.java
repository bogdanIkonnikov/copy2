package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.web.dto.question.AddQuestionRequest;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.repository.entity.Test;

import java.util.List;

@Component
public class QuestionMapper {
    public QuestionWithAnswersResponse toResponseWithAnswers(Question question, List<AnswerResponse> answerResponses) {
        QuestionWithAnswersResponse questionResponse = new QuestionWithAnswersResponse();
        questionResponse.setId(question.getId());
        questionResponse.setType(question.getType());
        questionResponse.setContent(question.getContent());
        questionResponse.setAnswers(answerResponses);
        return questionResponse;
    }
    public Question toQuestion(AddQuestionRequest addQuestionRequest, Test test) {
        Question question = new Question();
        question.setType(addQuestionRequest.getType());
        question.setContent(addQuestionRequest.getContent());
        question.setTest(test);
        return question;
    }
    public QuestionResponse toResponse(Question question) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(question.getId());
        questionResponse.setType(question.getType());
        questionResponse.setContent(question.getContent());
        return questionResponse;
    }
    public QuestionLightResponse toLightResponse(Question question) {
        QuestionLightResponse response = new QuestionLightResponse();
        response.setId(question.getId());
        response.setContent(question.getContent());
        return response;
    }
}
