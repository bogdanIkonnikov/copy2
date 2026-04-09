package tbank.copy2.domain.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.exception.VerificationCodeExpiredException;
import tbank.copy2.domain.model.VerificationCodeModel;
import tbank.copy2.domain.repository.VerificationCodeModelRepository;

import java.time.LocalDateTime;

@Service
public class VerificationService {
    private final VerificationCodeModelRepository repository;
    private final MailService mailService;

    public VerificationService(MailService mailService, VerificationCodeModelRepository repository) {
        this.mailService = mailService;
        this.repository = repository;
    }

    @Transactional
    public boolean verifyCode(String email, String code) {
        VerificationCodeModel codeModel = repository.findByEmailAndCode(email, code);
        if (codeModel == null) throw new RuntimeException("Неверный код");
        if (codeModel.isExpired()) throw new VerificationCodeExpiredException("Истек срок действия кода");
        repository.delete(codeModel);
        return true;
    }

    @Transactional
    public void sendVerificationCode(String email) {
        String code = generateCode(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Код подтверждения регистрации");
        message.setText("Ваш код для входа: " + code);
        mailService.send(message);
    }

    private String generateCode(String email) {
        repository.deleteByEmail(email);
        repository.flush();
        VerificationCodeModel codeModel = new VerificationCodeModel();
        codeModel.setEmail(email);
        codeModel.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        codeModel.setCode(Math.round((Math.random() * 1000000)) + "");
        repository.save(codeModel);
        return codeModel.getCode();
    }
}