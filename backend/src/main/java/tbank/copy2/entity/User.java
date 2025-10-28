package tbank.copy2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @Column
    private LocalDateTime created_at;

    @Column
    private LocalDateTime updated_at;

    public User(String password_hash, String email, String username) {
        this.password_hash = password_hash;
        this.email = email;
        this.username = username;
    }


}
