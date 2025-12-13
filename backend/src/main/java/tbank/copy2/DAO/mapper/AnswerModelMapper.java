package tbank.copy2.DAO.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.repository.repository.QuestionRepository;
import tbank.copy2.service.model.AnswerModel;

@Component
public class AnswerModelMapper {
    private final QuestionRepository questionRepository;

    public AnswerModelMapper(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public AnswerModel toModel(Answer answer) {
        if (answer == null) return null;

        AnswerModel answerModel = new AnswerModel();
        answerModel.setId(answer.getId());
        answerModel.setQuestionId(answer.getQuestion() != null ? answer.getQuestion().getId() : null);
        answerModel.setContent(answer.getContent());
        answerModel.setIsCorrect(answer.getIsCorrect());
        answerModel.setCreated_at(answer.getCreated_at());
        answerModel.setUpdated_at(answer.getUpdated_at());
        return answerModel;
    }

    public Answer toEntity(AnswerModel answerModel) {
        if (answerModel == null) return null;

        Answer answer = new Answer();
        answer.setId(answerModel.getId());
        answer.setContent(answerModel.getContent());
        answer.setIsCorrect(answerModel.getIsCorrect());
        answer.setCreated_at(answerModel.getCreated_at());
        answer.setUpdated_at(answerModel.getUpdated_at());
        if (answerModel.getQuestionId() != null) {
            Question question = questionRepository.findById(answerModel.getQuestionId()).orElse(null);
            answer.setQuestion(question);
        }

        return answer;
    }
}