package tbank.copy2.service.repository;

import tbank.copy2.service.model.VerificationCodeModel;

import java.util.Optional;


public interface VerificationCodeModelRepository{
     VerificationCodeModel findByEmailAndCode(String email, String code);

    void delete(VerificationCodeModel codeModel);

    void save(VerificationCodeModel codeModel);
}
