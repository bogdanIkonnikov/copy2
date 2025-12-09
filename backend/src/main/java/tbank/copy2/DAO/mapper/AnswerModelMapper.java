package tbank.copy2.DAO.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.repository.QuestionRepository;
import tbank.copy2.service.model.AnswerModel;

@Component
public class AnswerModelMapper {
    private final QuestionRepository questionRepository;

    public AnswerModelMapper(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public AnswerModel toModel(Answer answer) {
        AnswerModel answerModel = new AnswerModel();
        answerModel.setId(answer.getId());
        answerModel.setQuestionId(answer.getQuestion().getId());
        answerModel.setContent(answer.getContent());
        answerModel.setIsCorrect(answer.getIsCorrect());
        answerModel.setCreated_at(answer.getCreated_at());
        answerModel.setUpdated_at(answer.getUpdated_at());
        return answerModel;
    }
    public Answer toEntity(AnswerModel answerModel) {
        Answer answer = new Answer();
        answer.setId(answerModel.getId());
        answer.setQuestion(questionRepository.getQuestionById(answerModel.getQuestionId()));
        answer.setContent(answerModel.getContent());
        answer.setIsCorrect(answerModel.getIsCorrect());
        answer.setCreated_at(answerModel.getCreated_at());
        answer.setUpdated_at(answerModel.getUpdated_at());
        return answer;
    }
}
