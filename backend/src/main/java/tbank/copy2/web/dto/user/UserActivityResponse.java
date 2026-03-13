package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UserActivityResponse {
    private LocalDate date;
    private int count;
}
