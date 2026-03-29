package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tbank.copy2.common.enums.Role;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
public class UserModel implements UserDetails {
    private Long id;

    private String username;

    private String email;

    private String password_hash;

    private Role role = Role.USER;

    private String photoUrl;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password_hash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
