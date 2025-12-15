package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.DAO.repository.AnswerModelRepository;
import tbank.copy2.DAO.repository.QuestionModelRepository;
import tbank.copy2.DAO.repository.TestModelRepository;
import tbank.copy2.service.model.AnswerModel;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.service.model.TestModel;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private AnswerModelRepository answerRepository;
    @Autowired
    private TestModelRepository repository;
    @Autowired
    private QuestionModelRepository questionRepository;

    public List<TestModel> getTests() {
        return repository.findAll();
    }

    public TestModel addTest(TestModel model) {
        return repository.save(model);
    }

    public TestModel getTestById(Long id) {
        return repository.findById(id);
    }

    protected void setNewAnswers(QuestionModel question, List<AnswerModel> answers){
        if (question.getId() == null) {
            question = questionRepository.save(question);
            questionRepository.flush();
        }

        List<Long> answerIdsFromRequest = new ArrayList<>();
        for (AnswerModel uAnswer : answers) {
            if (uAnswer.getId() != null) {
                answerIdsFromRequest.add(uAnswer.getId());
            }
        }
        System.out.println("Айдишники ответов из реквеста: " + answerIdsFromRequest);

        question.getAnswerModels().removeIf(model ->
                !answerIdsFromRequest.contains(model.getId())
        );
        answerRepository.flush();

        for (AnswerModel uAnswer : answers) {
            AnswerModel currentAnswer;
            boolean isNewAnswer = uAnswer.getId() == null;

            if (!isNewAnswer) {
                currentAnswer = answerRepository.findById(uAnswer.getId());
                System.out.println("Найденный текущий ответ из БД: " + currentAnswer);
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
                System.out.println("Текущий ответ после обновления полей: " + currentAnswer);
            } else {
                currentAnswer = new AnswerModel();
                System.out.println("Создан новый ответ: " + currentAnswer);
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
                currentAnswer.setQuestionId(question.getId());
                System.out.println("Новый ответ после установки полей: " + currentAnswer);
            }

            answerRepository.save(currentAnswer);
            System.out.println("Сохраненный текущий ответ: " + currentAnswer);
        }

    }

    @Transactional
    public boolean updateTest(TestModel uModel, Long testId) {
        TestModel model = repository.findById(testId);

        model.setName(uModel.getName());
        model.setDescription(uModel.getDescription());

        List<QuestionModel> questionsFromRequest = new ArrayList<>();
        System.out.println("Вопросы из реквеста: " + uModel.getQuestions());

        for (QuestionModel uQuestion : uModel.getQuestions()) {
            QuestionModel currentQuestion;
            boolean isExistingQuestion = uQuestion.getId() != null && uQuestion.getId() > 0;

            if (isExistingQuestion) {
                currentQuestion = questionRepository.findById(uQuestion.getId());
                currentQuestion.setTestId(testId);
                currentQuestion.setContent(uQuestion.getContent());
                currentQuestion.setType(uQuestion.getType());
            } else {
                currentQuestion = new QuestionModel();
                currentQuestion.setTestId(testId);
                currentQuestion.setContent(uQuestion.getContent());
                currentQuestion.setType(uQuestion.getType());
                currentQuestion = questionRepository.save(currentQuestion);
                questionRepository.flush();
            }
            System.out.println("Текущий вопрос перед установкой ответов: " + currentQuestion);
            setNewAnswers(currentQuestion, uQuestion.getAnswerModels());
            System.out.println("Текущий вопрос после установки ответов: " + currentQuestion);

            questionRepository.save(currentQuestion);
            System.out.println("Сохраненный текущий вопрос: " + currentQuestion);
            questionsFromRequest.add(currentQuestion);
            System.out.println("Список сохраненных поросов: " + questionsFromRequest);
        }
        if (model.getQuestions() != null) {
            model.getQuestions().clear();
        }
        model.getQuestions().addAll(questionsFromRequest);

        repository.save(model);
        return true;
    }

    public Long deleteById(Long id) {
        return repository.deleteById(id);
    }
}
