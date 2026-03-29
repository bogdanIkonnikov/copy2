package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    @Autowired
    private MailService service;
    @Autowired
    private NotificationModelRepository repository;

    @Scheduled(cron = "0 * * * * *")
    public void schedule() {
        List<NotificationModel> notifications = repository.findAllBySentAtBeforeAndSent(LocalDateTime.now(), false);

        for (NotificationModel n : notifications) {
            service.send(n.getEmail(), "Напоминание пройти тест: " + n.getTestName(), "Пришло время повторить тест: " + n.getTestName());
            n.setSent(true);
        }

        repository.saveAll(notifications);
    }
}
