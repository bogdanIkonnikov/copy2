package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tbank.copy2.DAO.repository.AnswerModelRepository;
import tbank.copy2.DAO.repository.QuestionModelRepository;
import tbank.copy2.DAO.repository.TestModelRepository;
import tbank.copy2.common.enums.Type;
import tbank.copy2.service.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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


    public TestsPageModel getTests(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TestModel> models = repository.findAll(pageable);
        TestsPageModel model = new TestsPageModel();
        model.setModels(models);
        model.setTotalModels(repository.findAll().size());
        model.setTotalPages((int) Math.ceil(model.getTotalModels() / (double) pageSize));
        return model;
    }

    public TestModel addTest(TestModel model) {
        return repository.save(model);
    }

    public TestModel addTest(TestFileModel model) {
        TestModel testModel = new TestModel();
        testModel.setName(model.getName());
        testModel.setDescription(model.getDescription());
        testModel.setUserId(1L);
        TestModel savedModel = repository.save(testModel);
        savedModel.setQuestions(parseQuestions(model.getFile(), savedModel.getId()));
        return repository.save(savedModel);
    }

    public List<QuestionModel> parseQuestions(MultipartFile file, Long id) {
        String line;
        int qCount = 0;
        int answersCount = 0;
        boolean questionFound = false;
        boolean answerFound = false;
        AnswerModel answerModel = new AnswerModel();
        QuestionModel questionModel = new QuestionModel();
        StringBuilder questionName = new StringBuilder();
        StringBuilder answerName = new StringBuilder();
        List<QuestionModel> questions = new ArrayList<>();
        List<AnswerModel> answers = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {


            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");

                for (String word : words) {
                    switch (word) {
                        case ("ВОПРОС:"):
                            if (answersCount - answers.size() == 1){
                                System.out.println("Поставили вариант ответа: " + answerName);
                                answerModel.setContent(answerName.toString());
                                answers.add(answerModel);
                                answerModel = new AnswerModel();
                                answerName.setLength(0);
                            }

                            if (qCount != 0) {
                                System.out.println("Это не первый вопрос так что ставим предыдущему ответы");
                                questionModel.setTestId(id);
                                questionModel.setContent("initial");
                                questionModel = questionRepository.save(questionModel);
                                for (AnswerModel aModel : answers) {
                                    aModel.setQuestionId(questionModel.getId());
                                }
                                questionModel.setContent(questionName.toString());
                                questionName = new StringBuilder();
                                if (answers.size() > 1) {
                                    questionModel.setType(Type.CHOICE);
                                } else questionModel.setType(Type.INPUT);
                                questionModel.setAnswerModels(answers);
                                answers = new ArrayList<>();
                                questions.add(questionModel);
                                questionRepository.save(questionModel);
                                questionModel = new QuestionModel();
                                System.out.println("Обновленный список вопросов: " + questions);
                            }

                            System.out.println("Дальше пойдёт содержимое вопроса: ");


                            answersCount = 0;
                            qCount++;
                            questionFound = true;
                            answerFound = false;
                            break;
                        case ("ОТВЕТ:"), ("ВАРИАНТ:"):
                            if (answersCount != 0) {
                                System.out.println("Поставили вариант ответа: " + answerName);
                                answerModel.setContent(answerName.toString());
                                answers.add(answerModel);
                                answerModel = new AnswerModel();
                                System.out.println("Обновленный список ответов: " + answers);

                                answerName.setLength(0);
                            }

                            if (word.equals("ОТВЕТ:")){
                                System.out.println("Дальше пойдёт содержимое ответа: ");
                                answerModel.setIsCorrect(true);
                            } else {
                                System.out.println("Дальше пойдёт содержимое варианта: ");
                                answerModel.setIsCorrect(false);
                            }

                            answersCount++;

                            questionFound = false;
                            answerFound = true;
                            break;
                        default: {
                            if (questionFound) {
                                System.out.print(" " + word);
                                questionName.append(" " + word);
                            }
                            if (answerFound) {
                                System.out.print(" " + word);
                                answerName.append(" " + word);
                            }
                            break;
                        }
                    }
                }
            }
            if (answersCount - answers.size() == 1){
                System.out.println("Поставили вариант ответа: " + answerName);
                answerModel.setContent(answerName.toString());
                answers.add(answerModel);
            }

            if (qCount != 0) {
                System.out.println("Это не первый вопрос так что ставим предыдущему ответы");
                questionModel.setTestId(id);
                questionModel.setContent("initial");
                questionModel = questionRepository.save(questionModel);
                for (AnswerModel aModel : answers) {
                    aModel.setQuestionId(questionModel.getId());
                }
                questionModel.setContent(questionName.toString());
                if (answers.size() > 1) {
                    questionModel.setType(Type.CHOICE);
                } else questionModel.setType(Type.INPUT);
                questionModel.setAnswerModels(answers);
                questions.add(questionModel);
                questionRepository.save(questionModel);
                System.out.println("Обновленный список вопросов: " + questions);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }

    public TestModel getTestById(Long id) {
        return repository.findById(id);
    }

    protected void setNewAnswers(QuestionModel question, List<AnswerModel> answers) {
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
