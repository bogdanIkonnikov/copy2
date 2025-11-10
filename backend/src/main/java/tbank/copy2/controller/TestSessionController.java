package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.testSession.*;
import tbank.copy2.service.TestSessionService;

@RestController
@RequestMapping("/api/test-sessions")
@Tag(name = "Сессия", description = "Операции, связанные с сессиями тестов")
public class TestSessionController {
    @Autowired
    private TestSessionService testSessionService;

    @Operation(summary = "Начать новую тестовую сессию")
    @PostMapping()
    public TestSessionResponse startSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для начала сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestSessionRequest.class))
            )
            @RequestBody AddTestSessionRequest addTestSessionRequest) {
        return testSessionService.startSession(addTestSessionRequest);
    }

    @Operation(summary = "Отправить ответ на вопрос")
    @PostMapping("/{sessionId}/answers")
    public AnswerSessionResponse answerSession(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные ответа на вопрос в сессии",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AnswerSessionRequest.class))
            )
            @RequestBody AnswerSessionRequest request,
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId) {
        return testSessionService.answerSession(request, sessionId);
    }

    @Operation(summary = "Получить статус сессии")
    @GetMapping("/{sessionId}")
    public TestSessionStatusResponse getSessionStatus(
            @Parameter(description = "Идентификатор сессии")
            @PathVariable Long sessionId) {
        return testSessionService.getTestSessionStatus(sessionId);
    }

}
