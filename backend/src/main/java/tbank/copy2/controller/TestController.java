package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.question.QuestionLightResponse;
import tbank.copy2.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.dto.test.AddTestRequest;
import tbank.copy2.dto.test.TestResponse;
import tbank.copy2.service.QuestionService;
import tbank.copy2.service.TestService;

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


    @Operation(summary = "Получить список тестов")
    @GetMapping("")
    public List<TestResponse> getTests() {
        return testService.getTests();
    }

    @Operation(summary = "Добавить новый тест")
    @PostMapping("/add")
    public void addTest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового теста",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestRequest.class))
            )
            @RequestBody @Valid AddTestRequest request) {
        testService.addTest(request);
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public TestResponse getTestById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id) {
        return testService.getTestById(id);
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
                    example = "true",
                    required = false)
            @RequestParam(name = "light", required = false, defaultValue = "false") boolean light) {
        if (light) {
            Map<String, List<QuestionLightResponse>> response = new HashMap<>();
            response.put("questions", questionService.getLightQuestionsByTestId(id));
            return response;
        }
        else {
            Map<String, List<QuestionWithAnswersResponse>> response = new HashMap<>();
            response.put("questions", questionService.getQuestionsByTestId(id));
            return response;
        }
    }

}

