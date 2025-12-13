package tbank.copy2.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.service.model.AnswerModel;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.web.dto.question.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {
    @Autowired
    private AnswerMapper answerMapper;

    public QuestionWithAnswersResponse toResponseWithAnswers(QuestionModel model) {
        QuestionWithAnswersResponse questionResponse = new QuestionWithAnswersResponse();
        questionResponse.setId(model.getId());
        questionResponse.setType(model.getType());
        questionResponse.setContent(model.getContent());
        List<AnswerModel> answerModels = model.getAnswerModels();
        List<AnswerResponse> answerResponses = answerModels.stream()
                        .map(a -> answerMapper.toResponse(a)).toList();
        questionResponse.setAnswers(answerResponses);
        return questionResponse;
    }
    public QuestionModel toModel(AddQuestionRequest addQuestionRequest) {
        QuestionModel model = new QuestionModel();
        model.setType(addQuestionRequest.getType());
        model.setContent(addQuestionRequest.getContent());
        model.setTestId(addQuestionRequest.getTestId());
        return model;
    }

    public QuestionModel toModel(UpdateQuestionRequest updateQuestionRequest, Long testId) {
        QuestionModel model = new QuestionModel();
        model.setType(updateQuestionRequest.getType());
        model.setContent(updateQuestionRequest.getContent());
        model.setTestId(testId);
        List<AnswerModel> answerModels = updateQuestionRequest.getAnswers().stream()
                .map(a -> answerMapper.toModel(a)).collect(Collectors.toList());
        model.setAnswerModels(answerModels);
        return model;
    }

    public QuestionResponse toResponse(QuestionModel model) {
        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setId(model.getId());
        questionResponse.setType(model.getType());
        questionResponse.setContent(model.getContent());
        return questionResponse;
    }
    public QuestionLightResponse toLightResponse(QuestionModel model) {
        QuestionLightResponse response = new QuestionLightResponse();
        response.setId(model.getId());
        response.setContent(model.getContent());
        return response;
    }
}
