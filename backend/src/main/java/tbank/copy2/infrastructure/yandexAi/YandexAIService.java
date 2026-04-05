package tbank.copy2.infrastructure.yandexAi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import tbank.copy2.domain.ai.AiService;
import tbank.copy2.infrastructure.yandexAi.dto.CompletionOptions;
import tbank.copy2.infrastructure.yandexAi.dto.Message;
import tbank.copy2.infrastructure.yandexAi.dto.YandexAIRequest;
import tbank.copy2.infrastructure.yandexAi.dto.YandexAIResponse;

import java.util.List;

@Component
public class YandexAIService implements AiService {

    private WebClient webClient;

    @Value("${yandex.api.key}")
    private String apiKey;

    @Value("${yandex.folder.id}")
    private String folderId;

    private static final String gptPrompt = """
            ЗАБУДЬ ВСЕ МОИ ПРЕДЫДУЩИЕ ЗАПРОСЫ
            Ты — узкоспециализированный парсер текстов в формат тестов.
            Твоя задача: извлечь вопросы и ответы из любого входящего текста и привести их к СТРОГОМУ формату.
            
            ПРАВИЛА ФОРМАТИРОВАНИЯ:
            1. Каждый вопрос начинается с ключевого слова "ВОПРОС:".
            2. Правильные ответы помечаются ключевым словом "ОТВЕТ:".
            3. Неправильные ответы помечаются ключевым словом "ВАРИАНТ:".
            4. Каждый вопрос должен содержать от 1 до 5 вариантов ответа. Из всех вопросов БОЛЬШАЯ ЧАСТЬ должна быть с 3-4 вариантами ответа. 
            Ты должен придумать остальные варианты ответов сам.
            Например ты написал 5 вопросов. Тогда только 1 (максимум!) из них должен иметь 1 вариант ответа, все остальные имеют 3-5 вариантов.
            Если у вопроса только один вариант ответа, он должен быть ОБЯЗАТЕЛЬНО правильным, помечаться как "ОТВЕТ:" и быть максимально емким.
            5. Ключевые слова ДОЛЖНЫ быть в верхнем регистре и заканчиваться двоеточием.
            6. После ключевого слова и двоеточия сразу идет пробел и затем текст.
            7. Между вопросами не должно быть лишнего текста. Только блоки ВОПРОС/ОТВЕТ/ВАРИАНТ.
            
            ПРИМЕР ВЫХОДА:
            ВОПРОС: Что такое инкапсуляция?
            ОТВЕТ: Скрытие внутренней реализации объекта.
            ВАРИАНТ: Наследование свойств от родителя.
            ВАРИАНТ: Способность метода работать с разными типами данных.
            
            ВОПРОС: 2+2?
            ОТВЕТ: 4
            
            ИНСТРУКЦИЯ ПО ЛОГИКЕ:
            Если в тексте не указано явно, какой ответ верный — выбирай наиболее логичный. Если текст — это просто поток мыслей, структурируй его так, чтобы получился осмысленный вопрос. 
            Не додумывай дополнительную информацию по другим темам, которые не казаны в изначальном тексте.
            """;

    YandexAIService() {
        this.webClient = WebClient.builder().baseUrl("https://llm.api.cloud.yandex.net/").build();
    }

    @Override
    public String structureText(String rawText) {
        String modelUri = "gpt://" + folderId + "/yandexgpt/latest";

        YandexAIRequest request = new YandexAIRequest(
                modelUri,
                new CompletionOptions(false, 0.3, 2000),
                List.of(
                        new Message("system", gptPrompt),
                        new Message("user", rawText)
                )
        );
        try {
            YandexAIResponse response = webClient.post()
                    .uri("foundationModels/v1/completion")
                    .header("Authorization", "Api-Key " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, clientResponse ->
                            clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                                System.err.println("Yandex API Error Details: " + errorBody);
                                return Mono.error(new RuntimeException("API Error: " + errorBody));
                            })
                    )
                    .bodyToMono(YandexAIResponse.class)
                    .block();

            if (response == null || response.result() == null) {
                throw new RuntimeException("Пустой ответ от Yandex GPT API");
            }
            return response.result().alternatives().get(0).message().text();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при вызове Yandex GPT API: " + e.getMessage(), e);
        }
    }
}
