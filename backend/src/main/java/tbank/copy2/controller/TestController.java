package tbank.copy2.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.dto.test.AddTestRequest;
import tbank.copy2.dto.test.FullTestResponse;
import tbank.copy2.dto.test.TestResponse;
import tbank.copy2.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class TestController {

    @Autowired
    private TestService testService;

    @Operation(summary = "Получить список тестов")
    @GetMapping()
    public List<TestResponse> getTests() {
        return testService.getTests();
    }

    @Operation(summary = "Добавить новый тест")
    @PostMapping("/add")
    public void addTest(@RequestBody @Valid AddTestRequest request) {
        testService.addTest(request);
    }

    @Operation(summary = "Получить тест по его id")
    @GetMapping("/{id}")
    public FullTestResponse getTestById(@PathVariable Long id) {
        return testService.getTestById(id);
    }

}

