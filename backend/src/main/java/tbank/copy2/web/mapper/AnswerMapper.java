package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.AnswerModel;
import tbank.copy2.web.dto.answer.AddAnswerRequest;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.web.dto.answer.UpdateAnswerRequest;

@Component
public class AnswerMapper {
    public AnswerResponse toResponse(AnswerModel model) {
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(model.getId());
        answerResponse.setIsCorrect(model.getIsCorrect());
        answerResponse.setContent(model.getContent());
        return answerResponse;
    }
    public AnswerModel toModel(AddAnswerRequest request) {
        AnswerModel model = new AnswerModel();
        model.setId(request.getAnswerId());
        model.setIsCorrect(request.getIsCorrect());
        model.setContent(request.getContent());
        return model;
    }
    public AnswerModel toModel(UpdateAnswerRequest request) {
        AnswerModel model = new AnswerModel();
        model.setId(request.getAnswerId());
        model.setIsCorrect(request.getIsCorrect());
        model.setContent(request.getContent());
        return model;
    }
}
