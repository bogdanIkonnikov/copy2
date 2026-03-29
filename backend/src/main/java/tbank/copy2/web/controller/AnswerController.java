package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.model.AnswerModel;
import tbank.copy2.web.dto.answer.AddAnswerRequest;
import tbank.copy2.web.dto.answer.AnswerResponse;
import tbank.copy2.domain.service.AnswerService;
import tbank.copy2.web.mapper.AnswerMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/answers")
@Tag(name = "Ответы", description = "Операции, связанные с ответами")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerMapper mapper;

    @Operation(summary = "Получить ответы по id вопроса")
    @GetMapping("/question/{id}")
    public List<AnswerResponse> getAnswersByQuestionId(
            @Parameter(description = "Идентификатор вопроса", example = "1")
            @PathVariable Long id) {
        List<AnswerResponse> responses = answerService.getAnswersByQuestionId(id)
                .stream()
                .map(m -> mapper.toResponse(m))
                .collect(Collectors.toList());
        return responses;
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
        AnswerModel model = mapper.toModel(addAnswerRequest);
        AnswerResponse response = mapper.toResponse(answerService.addAnswer(model));
        return response;
    }

}
