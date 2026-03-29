package tbank.copy2.DAO.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.infrastructure.persistence.entity.VerificationCode;
import tbank.copy2.domain.model.VerificationCodeModel;

@Component
public class VerificationCodeModelMapper {

    public VerificationCodeModel toModel(VerificationCode entity) {
        VerificationCodeModel model = new VerificationCodeModel();
        model.setId(entity.getId());
        model.setCode(entity.getCode());
        model.setEmail(entity.getEmail());
        model.setExpiryDate(entity.getExpiryDate());
        return model;
    }

    public VerificationCode toEntity(VerificationCodeModel model) {
        VerificationCode entity = new VerificationCode();
        entity.setId(model.getId());
        entity.setCode(model.getCode());
        entity.setExpiryDate(model.getExpiryDate());
        entity.setEmail(model.getEmail());
        return entity;
    }
}
