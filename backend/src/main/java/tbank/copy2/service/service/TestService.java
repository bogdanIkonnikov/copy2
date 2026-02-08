package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tbank.copy2.exception.FileParseException;
import tbank.copy2.exception.InvalidFileFormatException;
import tbank.copy2.service.repository.AnswerModelRepository;
import tbank.copy2.service.repository.QuestionModelRepository;
import tbank.copy2.service.repository.TestModelRepository;
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


    @Transactional
    public TestsPageModel getTests(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TestModel> models = repository.findAll(pageable);
        TestsPageModel model = new TestsPageModel();
        model.setModels(models);
        model.setTotalModels(repository.findAll().size());
        model.setTotalPages((int) Math.ceil(model.getTotalModels() / (double) pageSize));
        repository.deleteByUserIdAndVisible(1L, false);
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

        String fileName = file.getOriginalFilename();
        if (!fileName.endsWith(".txt")) throw new InvalidFileFormatException("Неверный формат файла");

        if (file.isEmpty()) {
            throw new InvalidFileFormatException("Файл пуст");
        }

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
                            if (qCount > 0 && answers.isEmpty()) {
                                throw new InvalidFileFormatException("У вопроса №" + qCount + " нет вариантов ответа");
                            }

                            if (answersCount - answers.size() == 1) {
                                answerModel.setContent(answerName.toString());
                                answers.add(answerModel);
                                answerModel = new AnswerModel();
                                answerName.setLength(0);
                            }

                            if (qCount != 0) {
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
                            }

                            answersCount = 0;
                            qCount++;
                            questionFound = true;
                            answerFound = false;
                            break;
                        case ("ОТВЕТ:"), ("ВАРИАНТ:"):
                            if (answersCount != 0) {
                                answerModel.setContent(answerName.toString());
                                answers.add(answerModel);
                                answerModel = new AnswerModel();

                                answerName.setLength(0);
                            }

                            if (word.equals("ОТВЕТ:")) {
                                answerModel.setIsCorrect(true);
                            } else {
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
            if (answersCount - answers.size() == 1) {
                answerModel.setContent(answerName.toString());
                answers.add(answerModel);
            }

            if (qCount != 0) {
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
            }
        } catch (IOException e) {
            throw new FileParseException("Не удалось прочитать файл: " + e.getMessage());
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

        question.getAnswerModels().removeIf(model ->
                !answerIdsFromRequest.contains(model.getId())
        );
        answerRepository.flush();

        List<AnswerModel> answerModels = new ArrayList<>();

        for (AnswerModel uAnswer : answers) {
            AnswerModel currentAnswer;
            boolean isNewAnswer = uAnswer.getId() == null;

            if (!isNewAnswer) {
                currentAnswer = answerRepository.findById(uAnswer.getId());
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
            } else {
                currentAnswer = new AnswerModel();
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
                currentAnswer.setQuestionId(question.getId());
            }

            answerModels.add(currentAnswer);
        }
        question.setAnswerModels(answerModels);
    }

    @Transactional
    public boolean updateTest(TestModel uModel, Long testId) {
        TestModel model = repository.findById(testId);

        model.setName(uModel.getName());
        model.setDescription(uModel.getDescription());

        List<QuestionModel> questionsFromRequest = new ArrayList<>();

        for (QuestionModel uQuestion : uModel.getQuestions()) {
            QuestionModel currentQuestion;
            boolean isExistingQuestion = uQuestion.getId() != null && uQuestion.getId() > 0;

            if (isExistingQuestion) {
                currentQuestion = questionRepository.findById(uQuestion.getId());

                System.out.println(currentQuestion);

                currentQuestion.setTestId(testId);
                currentQuestion.setContent(uQuestion.getContent());
                currentQuestion.setType(uQuestion.getType());

                System.out.println(currentQuestion);
            } else {
                currentQuestion = new QuestionModel();
                currentQuestion.setTestId(testId);
                currentQuestion.setContent(uQuestion.getContent());
                currentQuestion.setType(uQuestion.getType());
                currentQuestion = questionRepository.save(currentQuestion);
                questionRepository.flush();
            }
            setNewAnswers(currentQuestion, uQuestion.getAnswerModels());

            questionsFromRequest.add(currentQuestion);
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

    public TestsPageModel searchTest(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TestModel> testPage = repository.findByNameContainingIgnoreCase(keyword, pageable);


        List<TestModel> tests = new ArrayList<>(testPage.getContent());


        tests.sort((p1, p2) -> {
            int relevance1 = calculateRelevance(p1.getName(), keyword);
            int relevance2 = calculateRelevance(p2.getName(), keyword);
            return Integer.compare(relevance2, relevance1);
        });
        TestsPageModel pageModel = new TestsPageModel();
        pageModel.setTotalPages(testPage.getTotalPages());
        pageModel.setTotalModels((int) testPage.getTotalElements());
        pageModel.setModels(tests);


        return pageModel;
    }

    private int calculateRelevance(String testName, String keyword) {
        String lowerName = testName.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();

        if (lowerName.equals(lowerKeyword)) {
            return 5;
        }
        if (lowerName.startsWith(lowerKeyword)) {
            return 4;
        }
        if (lowerName.endsWith(lowerKeyword)) {
            return 3;
        }
        if (lowerName.contains(" " + lowerKeyword + " ")) {
            return 2;
        }
        if (lowerName.contains(lowerKeyword)) {
            return 1;
        }
        return 0;

    }

}
