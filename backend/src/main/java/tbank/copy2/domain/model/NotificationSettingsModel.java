package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.NotificationType;

@Data
@NoArgsConstructor
public class NotificationSettingsModel {
    private Long id;

    private NotificationType type;

    private Long testId;

    private String testName;

    private Long userId;
}
