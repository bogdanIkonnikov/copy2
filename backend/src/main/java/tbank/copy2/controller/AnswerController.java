package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.dto.answer.AnswerResponse;
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

}
