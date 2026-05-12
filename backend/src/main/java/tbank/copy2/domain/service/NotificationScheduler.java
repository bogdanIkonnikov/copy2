package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.NotificationStatus;
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

    @Scheduled(cron = "0 */10 * * * *")
    public void schedule() {
        List<NotificationModel> notifications = repository.findAllToProcess(LocalDateTime.now());

        for (NotificationModel n : notifications) {
            int updated = repository.updateStatus(n.getId(), n.getVersion(), NotificationStatus.PROCESSING);

            if (updated > 0) {
                EmailNotificationModel message = new EmailNotificationModel();
                message.setSubject("Напоминание пройти тест: " + n.getTestName());
                message.setText("Пришло время повторить тест: " + n.getTestName());
                message.setTo(new String[]{n.getEmail()});
                try {
                    kafkaTemplate.send("notification-topic", message).whenComplete((result, ex) -> {
                        if (ex == null) {
                            repository.updateStatusFinal(n.getId(), NotificationStatus.SENT, null);
                        } else {
                            repository.updateStatusFinal(n.getId(), NotificationStatus.ERROR, ex.getMessage());
                        }
                    });
                } catch (Exception e) {
                    repository.updateStatusFinal(n.getId(), NotificationStatus.ERROR, e.getMessage());
                }
            }
        }
    }
}
