package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.VerificationCodeModelMapper;
import tbank.copy2.repository.repository.VerificationCodeRepository;
import tbank.copy2.service.model.VerificationCodeModel;
import tbank.copy2.service.repository.VerificationCodeModelRepository;

import java.util.Optional;

@Repository
public class VerifCodeModelRepoImpl implements VerificationCodeModelRepository {
    @Autowired
    private VerificationCodeRepository repository;
    @Autowired
    private VerificationCodeModelMapper mapper;

    @Override
    public VerificationCodeModel findByEmailAndCode(String email, String code) {
        return mapper.toModel(repository.findByEmailAndCode(email, code));
    }

    @Override
    public void delete(VerificationCodeModel codeModel) {
        repository.delete(mapper.toEntity(codeModel));
    }

    @Override
    public void save(VerificationCodeModel codeModel) {
        repository.save(mapper.toEntity(codeModel));
    }
}
