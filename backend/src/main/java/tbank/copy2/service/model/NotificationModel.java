package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotificationModel {
    private Long id;

    private Long testId;

    private String testName;

    private Long userId;

    private String email;

    private Boolean isEnabled;

    private LocalDateTime last_sent_at;

    private LocalDateTime next_sent_at;

    private int currentStep;
}
