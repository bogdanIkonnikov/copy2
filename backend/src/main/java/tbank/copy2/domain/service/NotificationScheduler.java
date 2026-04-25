package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.EmailNotificationModel;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    @Autowired
    private NotificationModelRepository repository;

    private final KafkaTemplate<String, EmailNotificationModel> kafkaTemplate;

    public NotificationScheduler(KafkaTemplate<String, EmailNotificationModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "0 * * * * *")
    public void schedule() {
        List<NotificationModel> notifications = repository.findAllBySentAtBeforeAndSent(LocalDateTime.now(), false);

        for (NotificationModel n : notifications) {
            if (repository.updateIfVersionMatches(n.getId(), n.getVersion())) {
                EmailNotificationModel message = new EmailNotificationModel();
                message.setSubject("Напоминание пройти тест: " + n.getTestName());
                message.setText("Пришло время повторить тест: " + n.getTestName());
                message.setTo(new String[]{n.getEmail()});
                kafkaTemplate.send("notification-topic", message);
            }
        }
    }
}
