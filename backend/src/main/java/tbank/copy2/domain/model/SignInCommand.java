package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInCommand {
    private String email;
    private String password;
}
