package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.dto.question.QuestionResponse;
import tbank.copy2.service.QuestionService;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
@Tag(name = "Вопросы", description = "Операции, связанные с вопросами")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Operation(summary = "Получить вопросы по id теста")
    @GetMapping("/test/{id}")
    public List<QuestionResponse> getQuestionsByTestId(
            @Parameter(description = "Идентификатор теста", example = "1")
            @RequestParam Long id) {
        return questionService.getQuestionsByTestId(id);
    }


}
