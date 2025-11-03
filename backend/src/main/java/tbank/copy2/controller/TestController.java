package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.test.AddTestRequest;
import tbank.copy2.dto.test.TestResponse;
import tbank.copy2.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Тесты", description = "Операции, связанные с тестами")
public class TestController {

    @Autowired
    private TestService testService;

    @Operation(summary = "Получить список id тестов")
    @GetMapping("/id")
    public List<Long> getTestsId() {
        return testService.getTestsId();
    }

    @Operation(summary = "Добавить новый тест")
    @PostMapping("/add")
    public void addTest(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления нового теста",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AddTestRequest.class))
            )
            @RequestBody @Valid AddTestRequest request) {
        testService.addTest(request);
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public TestResponse getTestById(@Parameter(description = "Идентификатор теста", example = "1") @PathVariable Long id) {
        return testService.getTestById(id);
    }

}

