package tbank.copy2.web.dto.user;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class CurrentUser {
    private Long userId;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
