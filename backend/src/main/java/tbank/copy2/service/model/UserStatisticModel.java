package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserStatisticModel {
    private Long id;
    private Long userId;
    private boolean isNew;
    private Long totalAnswers;
    private Long correctAnswers;
    private int currentStreak;
    private int longestStreak;
    private LocalDate lastTestDate;
}
