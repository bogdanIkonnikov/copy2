package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.model.TestSessionAnswerModel;
import tbank.copy2.domain.model.TestSessionModel;
import tbank.copy2.domain.model.TestSessionResponseModel;
import tbank.copy2.domain.service.TestSessionService;
import tbank.copy2.web.dto.testSession.*;
import tbank.copy2.web.mapper.TestSessionMapper;

@RestController
@RequestMapping("/api/test-sessions/anonymous")
@Tag(name = "Сессия", description = "Операции, связанные с сессиями тестов. Для неавторизованных пользователей!")
public class TestSessionNoAuthController {
    @Autowired
    private TestSessionService service;
    @Autowired
    private TestSessionMapper mapper;

    @Operation(summary = "Начать новую тестовую сессию")
    @PostMapping()
    public TestSessionResponse startSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для начала сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestSessionRequest.class))
            )
            @Valid
            @RequestBody AddTestSessionRequest addTestSessionRequest) {
        TestSessionModel model = mapper.toModel(addTestSessionRequest, null);
        TestSessionModel saved = service.startSession(model);
        return mapper.toSessionResponse(saved);
    }

    @Operation(summary = "Начать новую тестовую сессию по неправильным вопросам")
    @PostMapping("/start-wrong/{id}")
    public TestSessionResponse startWrongSession(
            @Parameter(description = "Идентификатор сессии")
            @Positive
            @PathVariable("id") Long id) {
        TestSessionModel saved = service.startWrongSession(id);
        return mapper.toSessionResponse(saved);
    }

    @Operation(summary = "Отправить ответ на вопрос")
    @PostMapping("/{sessionId}/answers")
    public AnswerSessionResponse answerSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные ответа на вопрос в сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerSessionRequest.class))
            )
            @Valid
            @RequestBody AnswerSessionRequest request,
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId) {
        TestSessionAnswerModel model = mapper.toModel(request, sessionId);
        TestSessionResponseModel responseModel = service.answerSession(model, sessionId);
        return mapper.toResponse(responseModel);
    }

    @Operation(summary = "Получить статус сессии")
    @GetMapping("/{sessionId}")
    public TestSessionStatusResponse getSessionStatus(
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId) {
        TestSessionModel model = service.getTestSessionStatus(sessionId);
        return mapper.toSessionStatusResponse(model);
    }

    @Operation(summary = "Завершить сессию")
    @PostMapping("/{sessionId}/finish")
    public TestSessionStatusResponse finishSession(
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId) {
        TestSessionModel model = service.finishSession(sessionId);
        return mapper.toSessionStatusResponse(model);
    }

}
