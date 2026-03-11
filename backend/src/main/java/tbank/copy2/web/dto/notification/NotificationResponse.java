package tbank.copy2.web.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationResponse {
    private Long id;
    private String testName;
    private String nextSentAt;
    private int currentStep;
    private boolean isEnabled;
}
