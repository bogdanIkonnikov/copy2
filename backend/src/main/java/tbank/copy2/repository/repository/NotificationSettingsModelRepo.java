package tbank.copy2.repository.repository;

import tbank.copy2.service.model.NotificationSettingsModel;

public interface NotificationSettingsModelRepo {
    NotificationSettingsModel findById(Long id);

    void save(NotificationSettingsModel n);
}
