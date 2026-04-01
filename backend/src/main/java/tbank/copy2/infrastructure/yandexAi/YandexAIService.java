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
                        new Message("system", "Ты — узкоспециализированный парсер текстов в формат тестов. \n" +
                                "Твоя задача: извлечь вопросы и ответы из любого входящего текста и привести их к СТРОГОМУ формату.\n" +
                                "\n" +
                                "ПРАВИЛА ФОРМАТИРОВАНИЯ:\n" +
                                "1. Каждый вопрос начинается с ключевого слова \"ВОПРОС:\".\n" +
                                "2. Правильные ответы помечаются ключевым словом \"ОТВЕТ:\".\n" +
                                "3. Неправильные ответы помечаются ключевым словом \"ВАРИАНТ:\".\n" +
                                "4. Ключевые слова ДОЛЖНЫ быть в верхнем регистре и заканчиваться двоеточием.\n" +
                                "5. После ключевого слова и двоеточия сразу идет текст (на той же строке или следующей — неважно, но твой парсер ждет пробел).\n" +
                                "6. Между вопросами не должно быть лишнего текста. Только блоки ВОПРОС/ОТВЕТ/ВАРИАНТ.\n" +
                                "\n" +
                                "ПРИМЕР ВЫХОДА:\n" +
                                "ВОПРОС: Что такое инкапсуляция?\n" +
                                "ОТВЕТ: Скрытие внутренней реализации объекта.\n" +
                                "ВАРИАНТ: Наследование свойств от родителя.\n" +
                                "ВАРИАНТ: Способность метода работать с разными типами данных.\n" +
                                "\n" +
                                "ВОПРОС: Какие из перечисленных классов относятся к Collection API в Java?\n" +
                                "ОТВЕТ: ArrayList\n" +
                                "ОТВЕТ: HashSet\n" +
                                "ВАРИАНТ: String\n" +
                                "ВАРИАНТ: Optional\n" +
                                "\n" +
                                "ИНСТРУКЦИЯ ПО ЛОГИКЕ:\n" +
                                "Если в тексте не указано явно, какой ответ верный — выбирай наиболее логичный. Если текст — это просто поток мыслей, структурируй его так, чтобы получился осмысленный вопрос."),
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
