package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserActivityResponse {
    private String date;
    private int count;
}
