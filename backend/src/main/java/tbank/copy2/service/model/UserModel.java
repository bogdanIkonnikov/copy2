package tbank.copy2.service.model;

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

    private Role role;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    // Возвращаем список прав пользователя (в нашем случае - его роль)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.password_hash;
    }

    // Эти методы обычно возвращают true, если ты не планируешь сложную логику блокировки
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
