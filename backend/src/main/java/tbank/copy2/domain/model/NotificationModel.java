package tbank.copy2.domain.model;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.NotificationStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotificationModel {
    private Long id;

    private Long testId;

    private String testName;

    private String email;

    private int version;

    private NotificationSettingsModel settings;

    private LocalDateTime sent_at;

    private NotificationStatus status;

    private String errorMessage;
}
