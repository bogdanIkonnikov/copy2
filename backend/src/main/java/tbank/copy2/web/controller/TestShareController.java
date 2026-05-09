package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.model.QuestionModel;
import tbank.copy2.domain.model.TestModel;
import tbank.copy2.domain.service.QuestionService;
import tbank.copy2.domain.service.TestService;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.web.dto.user.CurrentUser;
import tbank.copy2.web.mapper.QuestionMapper;
import tbank.copy2.web.mapper.TestMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/modules/share")
@Tag(name = "Тесты (без авторизации)", description = "Операции, связанные с тестами (без авторизации)")
public class TestShareController {
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper mapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;

    @Operation(summary = "Получить тест по токену")
    @GetMapping("/{shareToken}")
    public TestResponse getByShareToken(@PathVariable String shareToken, @AuthenticationPrincipal CurrentUser user) {
        TestModel model = testService.getByShareToken(shareToken);
        return mapper.toTestResponse(model, user == null ? null : user.getUserId());
    }
    @Operation(summary = "Получить вопросы теста")
    @GetMapping("/{shareToken}/questions")
    public Object getQuestionsByTestIdLight(
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken,
            @Parameter(description = "Сжатая версия вопросов",
                    example = "true")
            @RequestParam(name = "light", required = false, defaultValue = "false") boolean light) {
        List<QuestionModel> models = questionService.getQuestionsByShareToken(shareToken);
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

    @Operation(summary = "Получить вопрос")
    @GetMapping ("/{shareToken}/{questionId}")
    public QuestionWithAnswersResponse getQuestion(
            @Parameter(description = "Идентификатор теста", example = "1")
            @PathVariable Long questionId,
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken) {

        QuestionModel model = questionService.getQuestionById(questionId, shareToken);
        return questionMapper.toResponseWithAnswers(model);
    }
}
