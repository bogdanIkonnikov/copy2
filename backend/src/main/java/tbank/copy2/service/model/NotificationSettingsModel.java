package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationSettingsModel {
    private Long userId;
    private int step0;
    private int step1;
    private int step2;
    private int step3;
    private int step4;
    private int step5;
}
