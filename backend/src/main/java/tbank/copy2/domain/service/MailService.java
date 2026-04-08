package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class MailService {
    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void send(SimpleMailMessage message) {
        message.setFrom("krefature@mail.ru");
        mailSender.send(message);
    }
}
