package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.repository.repository.NotificationSettingsModelRepo;
import tbank.copy2.service.model.NotificationModel;
import tbank.copy2.service.model.NotificationSettingsModel;
import tbank.copy2.service.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
public class NotificationScheduler {
    @Autowired
    private MailService service;
    @Autowired
    private NotificationModelRepository repository;
    @Autowired
    private NotificationSettingsModelRepo settingsRepo;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void schedule() {
        List<NotificationModel> notifications = repository.findAllForRemind(LocalDateTime.now());

        for (NotificationModel n : notifications) {
            service.send(n.getEmail(), "Напоминание пройти тест: " + n.getTestName(), "Пришло время повторить тест: " + n.getTestName());
            updateToNextStep(n);
        }

    }

    private void updateToNextStep(NotificationModel n) {
        NotificationSettingsModel settings = settingsRepo.findById(n.getUserId());
        int minutes = switch (n.getCurrentStep()) {
            case 0 -> settings.getStep0();
            case 1 -> settings.getStep1();
            case 2 -> settings.getStep2();
            case 3 -> settings.getStep3();
            case 4 -> settings.getStep4();
            default -> settings.getStep5();
        };
        n.setLast_sent_at(LocalDateTime.now());
        n.setNext_sent_at(LocalDateTime.now().plusMinutes(minutes));
        n.setCurrentStep(n.getCurrentStep() + 1);

        repository.save(n);
    }
}
