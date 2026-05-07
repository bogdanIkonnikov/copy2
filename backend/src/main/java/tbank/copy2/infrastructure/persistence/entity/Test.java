package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tbank.copy2.common.enums.AccessMode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "test_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<TestSession> sessions;

    @OneToMany(mappedBy = "test", cascade = CascadeType.REMOVE)
    private List<TestAccess> accesses = new ArrayList<>();

    @Column
    private String description;

    @org.hibernate.annotations.BatchSize(size = 20)
    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @Column(nullable = false)
    private Boolean visible = true;

    @Column(name = "access_mode", nullable = false)
    private AccessMode accessMode = AccessMode.PRIVATE;

    @Column(name = "share_token", unique = true)
    private String shareToken;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", testName='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ownerId=" + (user != null ? user.getId() : null) +
                ", createdAt=" + created_at +
                ", updatedAt=" + updated_at +
                '}';
    }
}
