package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class UserAnswerModel {
    private Long id;

    private Long sessionId;

    private Long questionId;

    private Boolean correct;

    private LocalDateTime created_at;
}
