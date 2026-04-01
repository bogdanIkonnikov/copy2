package tbank.copy2.infrastructure.synonymChecker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tbank.copy2.domain.ai.SynonymCheckService;
import tbank.copy2.infrastructure.synonymChecker.dto.SynonymRequest;
import tbank.copy2.infrastructure.synonymChecker.dto.SynonymResponse;

@Service
public class SynonymChecker implements SynonymCheckService {
    private final WebClient webClient;

    public SynonymChecker(WebClient.Builder webClientBuilder, @Value("${synonym-service.url:http://localhost:8000}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public boolean check(String correctAnswer, String currentAnswer) {
        SynonymRequest request = new SynonymRequest(correctAnswer, currentAnswer, 0.75);
        SynonymResponse response = webClient.post()
                .uri("/check-answer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SynonymResponse.class)
                .block();
        return response.isCorrect();
    }
}
