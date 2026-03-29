package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


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
