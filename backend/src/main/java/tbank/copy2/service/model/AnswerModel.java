package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Data
@NoArgsConstructor
public class AnswerModel {
    private Long id;

    private Long questionId;

    private String content;

    private Boolean isCorrect;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
