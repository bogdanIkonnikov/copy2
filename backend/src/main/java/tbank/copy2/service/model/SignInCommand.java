package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignInCommand {
    private String username;
    private String password;
}
