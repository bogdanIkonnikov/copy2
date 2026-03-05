package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.NotificationSettings;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.NotificationSettingsModel;

@Component
public class NotificationSettingsMapper {
    @Autowired
    private UserRepository userRepository;

    public NotificationSettings toEntity(NotificationSettingsModel model){
        NotificationSettings settings = new NotificationSettings();
        settings.setId(model.getUserId());
        settings.setUser(userRepository.findById(model.getUserId()).orElse(null));
        settings.setStep1(model.getStep1());
        settings.setStep2(model.getStep2());
        settings.setStep3(model.getStep3());
        settings.setStep4(model.getStep4());
        settings.setStep5(model.getStep5());
        return settings;
    }

    public NotificationSettingsModel toModel(NotificationSettings entity){
        NotificationSettingsModel model = new NotificationSettingsModel();
        model.setUserId(entity.getUser().getId());
        model.setStep1(entity.getStep1());
        model.setStep2(entity.getStep2());
        model.setStep3(entity.getStep3());
        model.setStep4(entity.getStep4());
        model.setStep5(entity.getStep5());
        return model;
    }
}
