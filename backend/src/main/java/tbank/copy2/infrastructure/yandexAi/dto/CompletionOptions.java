package tbank.copy2.infrastructure.yandexAi.dto;

public record CompletionOptions(boolean stream, double temperature, int maxTokens) {
}
