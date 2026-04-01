package tbank.copy2.infrastructure.yandexAi.dto;

import java.util.List;

public record YandexAIRequest(String modelUri,
                              CompletionOptions completionOptions,
                              List<Message> messages) {
}
