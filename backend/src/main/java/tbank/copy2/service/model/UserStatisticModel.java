package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserStatisticModel {
    private Long id;
    private Long userId;
    private Long totalAnswers;
    private Long correctAnswers;
    private int currentStreak;
    private int longestStreak;
    private LocalDateTime lastTestDate;
}
