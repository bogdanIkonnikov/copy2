package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.question.AddQuestionRequest;
import tbank.copy2.dto.question.QuestionResponse;
import tbank.copy2.service.QuestionService;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Вопросы", description = "Операции, связанные с вопросами")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Добавить новый вопрос")
    @PostMapping("/add")
    public QuestionResponse addQuestion(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового вопроса",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddQuestionRequest.class))
            )
            @RequestBody @Valid AddQuestionRequest addQuestionRequest) {
        return questionService.addQuestion(addQuestionRequest);
    }

}
