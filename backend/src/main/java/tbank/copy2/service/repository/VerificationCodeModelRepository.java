package tbank.copy2.service.repository;

import tbank.copy2.service.model.VerificationCodeModel;


public interface VerificationCodeModelRepository{
     VerificationCodeModel findByEmailAndCode(String email, String code);

    void delete(VerificationCodeModel codeModel);

    void save(VerificationCodeModel codeModel);

    void deleteByEmail(String email);

    void flush();
}
