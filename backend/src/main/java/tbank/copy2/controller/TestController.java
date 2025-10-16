package tbank.copy2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.dto.test.TestResponse;
import tbank.copy2.service.TestService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/modules")
    public List<TestResponse> getTests() {
        return testService.getTests();
    }
}
