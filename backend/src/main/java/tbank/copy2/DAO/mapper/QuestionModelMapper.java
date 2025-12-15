package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.service.model.AnswerModel;
import tbank.copy2.service.model.QuestionModel;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionModelMapper {
    @Autowired
    private AnswerModelMapper mapper;
    @Autowired
    private TestRepository testRepository;

    public QuestionModel toModel(Question question) {
        QuestionModel model = new QuestionModel();
        model.setId(question.getId());
        model.setContent(question.getContent());
        model.setType(question.getType());
        model.setTestId(question.getTest().getId());
        model.setCreated_at(question.getCreated_at());
        model.setUpdated_at(question.getUpdated_at());
        System.out.println("Сами ответы из сущности вопроса: " + question.getAnswers());
        List<AnswerModel> answerModels = question.getAnswers()
                .stream()
                .map(a -> mapper.toModel(a))
                .collect(Collectors.toList());
        System.out.println("Преобразованные модели ответов: " + answerModels);
        model.setAnswerModels(answerModels);
        System.out.println("Модель вопроса после преобразования: " + model);
        return model;
    }

    public Question toEntity(QuestionModel model) {
        Question question = new Question();
        question.setId(model.getId());
        question.setContent(model.getContent());
        question.setType(model.getType());
        question.setCreated_at(model.getCreated_at());
        question.setUpdated_at(model.getUpdated_at());
        List<Answer> answers = model.getAnswerModels().stream().map(a -> mapper.toEntity(a)).collect(Collectors.toList());
        question.setAnswers(answers);
        question.setTest(testRepository.findById(model.getTestId()).orElse(null));
        return question;
    }
}
