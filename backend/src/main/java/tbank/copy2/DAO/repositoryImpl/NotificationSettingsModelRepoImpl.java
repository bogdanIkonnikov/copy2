package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationSettingsModelMapper;
import tbank.copy2.repository.entity.Notification;
import tbank.copy2.repository.entity.NotificationSettings;
import tbank.copy2.repository.repository.NotificationSettingsModelRepo;
import tbank.copy2.repository.repository.NotificationSettingsRepository;
import tbank.copy2.service.model.NotificationSettingsModel;

@Repository
public class NotificationSettingsModelRepoImpl implements NotificationSettingsModelRepo {
    @Autowired
    private NotificationSettingsRepository repository;
    @Autowired
    private NotificationSettingsModelMapper mapper;

    @Override
    public NotificationSettingsModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public void save(NotificationSettingsModel n) {
        NotificationSettings settings = mapper.toEntity(n);
        if (repository.existsById(settings.getId())) settings.setNew(false);
        repository.save(settings);
    }

    @Override
    public NotificationSettingsModel findByUserId(Long userId) {
        return mapper.toModel(repository.findByUserId(userId));
    }


    @Override
    public boolean existsByUserId(Long userId) {
        return repository.existsByUserId(userId);
    }
}
