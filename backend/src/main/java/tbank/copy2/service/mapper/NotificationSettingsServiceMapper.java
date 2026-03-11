package tbank.copy2.service.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.service.model.NotificationSettingsModel;

@Component
public class NotificationSettingsServiceMapper {
    public void setIntervals(NotificationSettingsModel to, NotificationSettingsModel from) {
        to.setStep0(from.getStep0());
        to.setStep1(from.getStep1());
        to.setStep2(from.getStep2());
        to.setStep3(from.getStep3());
        to.setStep4(from.getStep4());
        to.setStep5(from.getStep5());
    }
}
