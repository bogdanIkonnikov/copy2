package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Data
@NoArgsConstructor
public class UserModel {
    private Long id;

    private String username;

    private String email;

    private String password_hash;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
