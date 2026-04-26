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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tbank.copy2.domain.model.QuestionModel;
import tbank.copy2.domain.model.TestFileModel;
import tbank.copy2.domain.model.TestModel;
import tbank.copy2.domain.model.TestsPageModel;
import tbank.copy2.web.dto.question.QuestionLightResponse;
import tbank.copy2.web.dto.question.QuestionWithAnswersResponse;
import tbank.copy2.web.dto.test.*;
import tbank.copy2.domain.service.QuestionService;
import tbank.copy2.domain.service.TestService;
import tbank.copy2.web.dto.user.CurrentUser;
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
    public TestPageResponse getTests(
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Positive @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal CurrentUser user) {
        TestsPageModel model = testService.getTests(page, size, user.getUserId());
        return mapper.toTestPageResponse(model, page, size);
    }

    @Operation(summary = "Получить список публичных тестов")
    @GetMapping("/public")
    public TestPageResponse getPublicTests(
            @PositiveOrZero @RequestParam(defaultValue = "0") int page,
            @Positive @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @AuthenticationPrincipal CurrentUser user) {
        TestsPageModel model = testService.getAlienPublicTests(page, size, user.getUserId(), keyword);
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
            @RequestBody @Valid AddTestRequest request,
    @AuthenticationPrincipal CurrentUser user) {
        TestModel model = testService.addTest(mapper.toModel(request, user.getUserId()));
        return mapper.toTestResponse(model);
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public TestWithOwnerResponse getTestById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id,
                                             @AuthenticationPrincipal CurrentUser user) {
        return mapper.toTestOwnerResponse(testService.getTestById(id), user.getUserId());
    }

    @Operation(summary = "Изменить режим доступа к тесту")
    @PutMapping("/{id}/access")
    public AccessResponse setAccessMode(@RequestBody @Valid AccessRequest request,
                                        @AuthenticationPrincipal CurrentUser user,
                                        @PathVariable Long id){
        TestModel model = testService.changeAccessMode(user.getUserId(), request.getAccessMode(), id);
        return mapper.toAccessResponse(model);
    }

    @Operation(summary = "Удалить тест по его id")
    @DeleteMapping("/{id}")
    public Long deleteById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id,
                           @AuthenticationPrincipal CurrentUser user) {
        return testService.deleteById(id, user.getUserId());
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
    public boolean updateTestById(@PathVariable Long id,
                                  @RequestBody @Valid UpdateTestRequest request,
                                  @AuthenticationPrincipal CurrentUser user) {
        TestModel model = mapper.toModel(request, id);
        return testService.updateTest(model, user.getUserId(), id);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TestResponse addFromFile(@RequestParam("name")
                                        @NotBlank(message = "Имя обязательно")
                                        String name,
                                    @RequestParam("description")
                                        String description,
                                    @RequestParam("file")
                                        @NotNull(message = "Файл обязателен")
                                        MultipartFile file,
                                    @AuthenticationPrincipal CurrentUser user){
        TestFileModel model = mapper.toModel(name, description, file, user.getUserId());

        return mapper.toTestResponse(testService.addTest(model));
    }

    @GetMapping("/search")
    public TestPageResponse searchTests(
            @RequestParam("keyWord") String keyWord,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @AuthenticationPrincipal CurrentUser user
    ){
        TestsPageModel model = testService.searchTest(user.getUserId(), keyWord, page, size);
        return mapper.toTestPageResponse(model, page, size);
    }

    @PostMapping(value = "/upload/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TestResponse addFromFileAI(@RequestParam("name")
                                    @NotBlank(message = "Имя обязательно")
                                    String name,
                                    @RequestParam("description")
                                    String description,
                                    @RequestParam("file")
                                    @NotNull(message = "Файл обязателен")
                                    MultipartFile file,
                                    @AuthenticationPrincipal CurrentUser user){
        TestFileModel model = mapper.toModel(name, description, file, user.getUserId());

        return mapper.toTestResponse(testService.addTestAI(model));
    }

    @GetMapping("/short")
    @Operation(description = "Получить список всех тестов текущего пользователя в кратком формате")
    public List<ShortTestResponse> getShortTests(@AuthenticationPrincipal CurrentUser user) {
        return mapper.toShortResponses(testService.getAllByUserId(user.getUserId()));
    }
}