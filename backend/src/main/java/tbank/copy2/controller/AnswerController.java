package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.answer.AddAnswerRequest;
import tbank.copy2.dto.answer.AnswerResponse;
import tbank.copy2.dto.question.AddQuestionRequest;
import tbank.copy2.service.AnswerService;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Ответы", description = "Операции, связанные с ответами")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Operation(summary = "Получить ответы по id вопроса")
    @GetMapping("/question/{id}")
    public List<AnswerResponse> getAnswersByQuestionId(
            @Parameter(description = "Идентификатор вопроса", example = "1")
            @PathVariable Long id) {

        return answerService.getAnswersByQuestionId(id);
    }

    @Operation(summary = "Добавить новый ответ")
    @PostMapping("/add")
    public AnswerResponse addAnswer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового ответа",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddAnswerRequest.class))
            )
            @RequestBody @Valid AddAnswerRequest addAnswerRequest) {
        return answerService.addAnswer(addAnswerRequest);
    }

}
