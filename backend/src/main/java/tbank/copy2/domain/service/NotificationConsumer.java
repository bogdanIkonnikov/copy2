package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import tbank.copy2.domain.model.EmailNotificationModel;

@Service
public class NotificationConsumer {
    @Autowired
    private MailService mailService;

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(EmailNotificationModel model){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setText(model.getText());
        msg.setTo(model.getTo());
        msg.setSubject(model.getSubject());
        mailService.send(msg);
    }
}
