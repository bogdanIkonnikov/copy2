package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.service.model.TestFileModel;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.service.model.TestsPageModel;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestPageResponse;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.service.service.QuestionService;
import tbank.copy2.service.service.TestService;
import tbank.copy2.web.dto.test.UpdateTestRequest;
import tbank.copy2.web.mapper.QuestionMapper;
import tbank.copy2.web.mapper.TestMapper;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Тесты", description = "Операции, связанные с тестами")
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private TestMapper mapper;


    @Operation(summary = "Получить список тестов")
    @GetMapping("")
    public TestPageResponse getTests(
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Positive @RequestParam(defaultValue = "10") int size) {
        TestsPageModel model = testService.getTests(page, size);
        return mapper.toTestPageResponse(model, page, size);
    }

    @Operation(summary = "Добавить новый тест")
    @PostMapping("")
    public TestResponse addTest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового теста",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestRequest.class))
            )
            @RequestBody @Valid AddTestRequest request) {
        TestModel model = testService.addTest(mapper.toModel(request));
        return mapper.toTestResponse(model);
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public TestResponse getTestById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id) {
        return mapper.toTestResponse(testService.getTestById(id));
    }

    @Operation(summary = "Удалить тест по его id")
    @DeleteMapping("/{id}")
    public Long deleteById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id) {
        return testService.deleteById(id);
    }

    @Operation(
            summary = "Получить список вопросов по id теста",
            description = "Возвращает список вопросов в зависимости от параметра light",
            responses = {
                    @ApiResponse(
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            oneOf = {
                                                    QuestionLightResponse.class,
                                                    QuestionWithAnswersResponse.class
                                            }
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}/questions")
    public Object getQuestionsByTestIdLight(
            @Parameter(description = "Идентификатор теста", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Сжатая версия вопросов",
                    example = "true")
            @RequestParam(name = "light", required = false, defaultValue = "false") boolean light) {
        List<QuestionModel> models = questionService.getQuestionsByTestId(id);
        if (light) {
            Map<String, List<QuestionLightResponse>> response = new HashMap<>();
            List<QuestionLightResponse> lightResponses = models.stream().map(m -> questionMapper.toLightResponse(m)).toList();
            response.put("questions", lightResponses);
            return response;
        }
        else {
            Map<String, List<QuestionWithAnswersResponse>> response = new HashMap<>();
            List<QuestionWithAnswersResponse> bigResponses = models.stream().map(m -> questionMapper.toResponseWithAnswers(m)).toList();
            response.put("questions", bigResponses);
            return response;
        }
    }

    @PutMapping("/{id}")
    public boolean updateTestById(@PathVariable Long id, @RequestBody @Valid UpdateTestRequest request) {
        TestModel model = mapper.toModel(request, id);
        return testService.updateTest(model, id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TestResponse addFromFile(@RequestParam("name")
                                        @NotBlank(message = "Имя обязательно")
                                        String name,
                                    @RequestParam("description")
                                        String description,
                                    @RequestParam("file")
                                        @NotNull(message = "Файл обязателен")
                                        MultipartFile file){
        TestFileModel model = mapper.toModel(name, description, file);
        return mapper.toTestResponse(testService.addTest(model));
    }

    @GetMapping("/search")
    public TestPageResponse searchTests(
            @RequestParam("keyWord") String keyWord,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        TestsPageModel model = testService.searchTest(keyWord, page, size);
        return mapper.toTestPageResponse(model, page, size);
    }
}