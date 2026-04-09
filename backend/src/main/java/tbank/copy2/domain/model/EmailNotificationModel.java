package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailNotificationModel {
    private String[] to;
    private String subject;
    private String text;
}
