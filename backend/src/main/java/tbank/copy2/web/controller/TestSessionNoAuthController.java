package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.model.TestSessionAnswerModel;
import tbank.copy2.domain.model.TestSessionModel;
import tbank.copy2.domain.model.TestSessionResponseModel;
import tbank.copy2.domain.service.TestSessionService;
import tbank.copy2.web.dto.testSession.*;
import tbank.copy2.web.mapper.TestSessionMapper;

@RestController
@RequestMapping("/api/test-sessions/share")
@Tag(name = "Сессия (без авторизации)", description = "Операции, связанные с сессиями тестов (без авторизации)")
public class TestSessionNoAuthController {
    @Autowired
    private TestSessionService service;
    @Autowired
    private TestSessionMapper mapper;

    @Operation(summary = "Начать новую тестовую сессию")
    @PostMapping("/{shareToken}")
    public TestSessionResponse startSession(
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для начала сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestSessionRequest.class))
            )
            @Valid
            @RequestBody AddTestSessionRequest addTestSessionRequest) {
        TestSessionModel model = mapper.toModel(addTestSessionRequest, null);
        TestSessionModel saved = service.startSession(model, shareToken);
        return mapper.toSessionResponse(saved);
    }

    @Operation(summary = "Начать новую тестовую сессию по неправильным вопросам")
    @PostMapping("/{shareToken}/{id}/start-wrong")
    public TestSessionResponse startWrongSession(
            @Parameter(description = "Идентификатор сессии")
            @Positive
            @PathVariable Long id,
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken) {
        Pair<TestSessionModel, String> saved = service.startWrongSession(id, shareToken);
        return mapper.toNoAuthResponse(saved.a, saved.b);
    }

    @Operation(summary = "Отправить ответ на вопрос")
    @PostMapping("/{shareToken}/{sessionId}/answers")
    public AnswerSessionResponse answerSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные ответа на вопрос в сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerSessionRequest.class))
            )
            @Valid
            @RequestBody AnswerSessionRequest request,
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId,
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken) {
        TestSessionAnswerModel model = mapper.toModel(request, sessionId);
        TestSessionResponseModel responseModel = service.answerSession(model, sessionId, shareToken);
        return mapper.toResponse(responseModel);
    }

    @Operation(summary = "Завершить сессию")
    @PostMapping("/{shareToken}/{sessionId}/finish")
    public TestSessionStatusResponse finishSession(
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId,
            @Parameter(description = "Токен теста")
            @NotNull(message = "Токен теста не может быть null")
            @PathVariable String shareToken) {
        TestSessionModel model = service.finishSession(sessionId, shareToken);
        return mapper.toSessionStatusResponse(model);
    }

}
