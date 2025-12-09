package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.service.model.AnswerModel;
import tbank.copy2.service.model.QuestionModel;

import java.util.List;

@Component
public class QuestionModelMapper {
    @Autowired
    private AnswerModelMapper mapper;

    public QuestionModel toModel(Question question) {
        QuestionModel model = new QuestionModel();
        model.setId(question.getId());
        model.setContent(question.getContent());
        model.setType(question.getType());
        model.setTestId(question.getTest().getId());
        model.setCreated_at(question.getCreated_at());
        model.setUpdated_at(question.getUpdated_at());
        List<AnswerModel> answerModels = question.getAnswers()
                .stream()
                .map(a -> mapper.toModel(a))
                .toList();
        model.setAnswerModels(answerModels);
        return model;
    }

    public Question toEntity(QuestionModel model) {
        Question question = new Question();
        question.setId(model.getId());
        question.setContent(model.getContent());
        question.setType(model.getType());
        question.setCreated_at(model.getCreated_at());
        question.setUpdated_at(model.getUpdated_at());
        List<Answer> answers = model.getAnswerModels().stream().map(a -> mapper.toEntity(a)).toList();
        question.setAnswers(answers);
        return question;
    }
}
