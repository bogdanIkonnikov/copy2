package tbank.copy2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Разрешаем CORS для всех путей
                .allowedOrigins("http://localhost:3000") // Разрешённые источники запросов
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешённые HTTP методы
                .allowedHeaders("*") // Разрешаем все заголовки
                .allowCredentials(true) // Разрешаем передачу cookies и авторизационных данных
                .maxAge(3600); // Время в секундах, в течение которого кэшируется результат preflight запроса
    }
}
