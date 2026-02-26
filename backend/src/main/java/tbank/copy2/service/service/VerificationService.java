package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.service.model.VerificationCodeModel;
import tbank.copy2.service.repository.VerificationCodeModelRepository;

@Service
public class VerificationService {
    @Autowired
    private VerificationCodeModelRepository repository;

    public boolean verifyCode(String email, String code) {
        VerificationCodeModel codeModel = repository.findByEmailAndCode(email, code);
        if (codeModel == null) throw new RuntimeException("Invalid verification code");
        return codeModel.isExpired();
    }
}