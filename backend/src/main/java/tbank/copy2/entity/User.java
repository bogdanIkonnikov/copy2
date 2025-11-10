package tbank.copy2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String avatar_url;

    @Column
    private LocalDate created_at;

    @Column
    private LocalDate updated_at;

    public User(String avatar_url, String password, String email, String username) {
        this.avatar_url = avatar_url;
        this.password = password;
        this.email = email;
        this.username = username;
    }


}
