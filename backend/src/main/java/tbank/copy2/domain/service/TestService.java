package tbank.copy2.domain.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tbank.copy2.common.enums.AccessLevel;
import tbank.copy2.common.enums.AccessMode;
import tbank.copy2.domain.ai.AiService;
import tbank.copy2.domain.repository.TestAccessModelRepository;
import tbank.copy2.exception.AccessDeniedException;
import tbank.copy2.exception.FileParseException;
import tbank.copy2.exception.InvalidFileFormatException;
import tbank.copy2.domain.repository.AnswerModelRepository;
import tbank.copy2.domain.repository.QuestionModelRepository;
import tbank.copy2.domain.repository.TestModelRepository;
import tbank.copy2.common.enums.Type;
import tbank.copy2.domain.model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class TestService {
    @Autowired
    private AnswerModelRepository answerRepository;
    @Autowired
    private TestModelRepository repository;
    @Autowired
    private QuestionModelRepository questionRepository;
    @Autowired
    private AiService aiService;
    @Autowired
    private TestAccessModelRepository accessRepository;


    public TestsPageModel getTests(int pageNumber, int pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TestModel> models = repository.findAllByUserId(pageable, userId);
        TestsPageModel model = new TestsPageModel();
        model.setModels(models);
        model.setTotalModels(repository.findAll().size());
        model.setTotalPages((int) Math.ceil(model.getTotalModels() / (double) pageSize));
        repository.deleteByUserIdAndVisible(userId, false);
        return model;
    }

    public TestsPageModel getAlienPublicTests(int pageNumber, int pageSize, Long userId) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<TestModel> models = repository.findAllAlienPublicTests(pageable, userId);
        TestsPageModel model = new TestsPageModel();
        model.setModels(models);
        model.setTotalModels(repository.findAllAlienPublicTests(userId).size());
        model.setTotalPages((int) Math.ceil(model.getTotalModels() / (double) pageSize));
        repository.deleteByUserIdAndVisible(userId, false);
        return model;
    }

    @Transactional
    public TestModel addTest(TestModel model) {
        TestModel savedModel = repository.save(model);

        TestAccessModel access = new TestAccessModel();
        access.setAccessLevel(AccessLevel.WRITE);
        access.setTestId(savedModel.getId());
        access.setSharedAt(LocalDateTime.now());
        access.setUserId(model.getUserId());

        savedModel.setAccesses(List.of(access));

        accessRepository.save(access);



        return savedModel;
    }

    @Transactional
    public TestModel addTest(TestFileModel model) {
        TestModel testModel = new TestModel();
        testModel.setName(model.getName());
        testModel.setAccessMode(AccessMode.PRIVATE);
        testModel.setShareToken(java.util.UUID.randomUUID().toString().substring(0, 20));
        testModel.setDescription(model.getDescription());
        testModel.setUserId(model.getUserId());
        TestModel savedModel = repository.save(testModel);

        TestAccessModel access = new TestAccessModel();
        access.setAccessLevel(AccessLevel.WRITE);
        access.setTestId(savedModel.getId());
        access.setSharedAt(LocalDateTime.now());
        access.setUserId(model.getUserId());
        accessRepository.save(access);

        savedModel.setAccesses(List.of(access));

        savedModel.setQuestions(parseQuestions(model.getFile(), savedModel.getId()));
        return savedModel;
    }

    @Transactional
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

                            if (answersCount - answers.size() == 1) {
                                answerModel.setContent(answerName.toString());
                                answers.add(answerModel);
                                answerModel = new AnswerModel();
                                answerName.setLength(0);
                            }

                            if (qCount != 0 && !questionName.isEmpty()) {
                                questionModel.setTestId(id);
                                questionModel.setContent("initial");
                                questionModel = questionRepository.save(questionModel);

                                Long questionId = questionModel.getId();
                                if (answers.isEmpty()) {
                                    throw new InvalidFileFormatException("У вопроса №" + qCount + " нет вариантов ответа");
                                }
                                answers = answers.stream()
                                        .filter(a -> !a.getContent().isEmpty())
                                        .limit(5).collect(Collectors.toList());
                                answers.forEach(aModel -> aModel.setQuestionId(questionId));
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
                            if (questionFound && (questionName.length() + word.length() < 99)) {
                                questionName.append(" " + word);
                            }
                            if (answerFound && (answerName.length() + word.length() < 99)) {
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

            if (qCount != 0 && !questionName.isEmpty()) {
                questionModel.setTestId(id);
                questionModel.setContent("initial");
                questionModel = questionRepository.save(questionModel);
                Long questionId = questionModel.getId();
                answers = answers.stream()
                        .filter(a -> !a.getContent().isEmpty())
                        .limit(5).collect(Collectors.toList());
                answers.forEach(aModel -> aModel.setQuestionId(questionId));
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

    @Transactional
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
    public boolean updateTest(TestModel uModel, Long userId, Long testId) {
        if (!repository.hasEditAccess(userId, testId)) throw new AccessDeniedException("У вас нет прав на редактирование этого теста!");
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

    public Long deleteById(Long id, Long userId) {
        if (!(repository.findById(id).getUserId() == userId)) throw new AccessDeniedException("У вас нет прав на удаление этого теста, удалить тест может только его создатель!");
        return repository.deleteById(id);
    }

    public TestsPageModel searchTest(Long userId, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<TestModel> testPage = repository.findByNameContainingIgnoreCase(keyword, userId, pageable);


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

    private MultipartFile parseAIFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileFormatException("Файл пуст");
        }

        StringBuilder sb = new StringBuilder("");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String rawText = sb.toString();

        String structuredText = aiService.structureText(rawText);

        System.out.println(structuredText);

        MultipartFile aiProcessedFile = new MockMultipartFile(
                "file",
                "aiParsed.txt",
                "text/plain",
                structuredText.getBytes(StandardCharsets.UTF_8)
        );

        return aiProcessedFile;
    }

    @Transactional
    public TestModel addTestAI(TestFileModel model) {
        TestModel testModel = new TestModel();
        testModel.setName(model.getName());
        testModel.setDescription(model.getDescription());
        testModel.setAccessMode(AccessMode.PRIVATE);
        testModel.setShareToken(java.util.UUID.randomUUID().toString().substring(0, 20));
        System.out.println(testModel.getShareToken());
        testModel.setUserId(model.getUserId());
        TestModel savedModel = repository.save(testModel);

        TestAccessModel access = new TestAccessModel();
        access.setAccessLevel(AccessLevel.WRITE);
        access.setTestId(savedModel.getId());
        access.setSharedAt(LocalDateTime.now());
        access.setUserId(model.getUserId());
        accessRepository.save(access);

        savedModel.setAccesses(List.of(access));

        savedModel.setQuestions(parseQuestions(parseAIFile(model.getFile()), savedModel.getId()));
        return savedModel;
    }

    public List<TestModel> getAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    public TestModel changeAccessMode(Long userId, AccessMode accessMode, Long id) {
        TestModel model = repository.findById(id);
        if (userId == model.getUserId()) {
            model.setAccessMode(accessMode);
            return repository.save(model);
        } else {
            throw new AccessDeniedException("У вас нет прав на изменение доступа этого теста, изменить доступ может только его создатель!");
        }
    }

    public TestModel getByShareToken(String shareToken) {
        TestModel model = repository.findByShareToken(shareToken);
        if (model == null) throw new EntityNotFoundException("Тест не найден");
        if (model.getAccessMode().equals(AccessMode.LINK)){
            return model;
        } else {
            throw new AccessDeniedException("У вас нет прав на просмотр этого теста, доступ к этому тесту по ссылке запрещён его создателем!");
        }
    }
}