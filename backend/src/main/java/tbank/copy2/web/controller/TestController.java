package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.service.service.QuestionService;
import tbank.copy2.service.service.TestService;
import tbank.copy2.web.dto.test.UpdateTestRequest;
import tbank.copy2.web.mapper.QuestionMapper;
import tbank.copy2.web.mapper.TestMapper;

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
    public List<TestResponse> getTests() {
        return testService.getTests().stream().map(t -> mapper.toTestResponse(t)).toList();
    }

    @Operation(summary = "Добавить новый тест")
    @PostMapping("")
    public void addTest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового теста",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestRequest.class))
            )
            @RequestBody @Valid AddTestRequest request) {
        testService.addTest(mapper.toModel(request));
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public TestResponse getTestById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id) {
        return mapper.toTestResponse(testService.getTestById(id));
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

}

