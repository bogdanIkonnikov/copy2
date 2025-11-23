package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.web.dto.answer.AddAnswerRequest;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;

@Component
public class AnswerMapper {
    public AnswerResponse toResponse(Answer answer) {
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(answer.getId());
        answerResponse.setIsCorrect(answer.getIsCorrect());
        answerResponse.setContent(answer.getContent());
        return answerResponse;
    }
    public Answer toEntity(AddAnswerRequest request, Question question) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setIsCorrect(request.getIsCorrect());
        answer.setContent(request.getContent());
        return answer;
    }
}
