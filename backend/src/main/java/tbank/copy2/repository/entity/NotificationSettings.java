package tbank.copy2.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class NotificationSettings {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, name = "step_0")
    private int step0 = 20;

    @Column(name = "step_1")
    private int step1 = 480;

    @Column(name = "step_2")
    private int step2 = 1440;

    @Column(name = "step_3")
    private int step3 = 2880;

    @Column(name = "step_4")
    private int step4 = 10080;

    @Column(name = "step_5")
    private int step5 = 20160;
}