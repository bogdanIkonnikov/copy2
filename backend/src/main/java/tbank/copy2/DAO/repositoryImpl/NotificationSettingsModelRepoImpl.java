package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationSettingsMapper;
import tbank.copy2.repository.repository.NotificationSettingsModelRepo;
import tbank.copy2.repository.repository.NotificationSettingsRepository;
import tbank.copy2.service.model.NotificationSettingsModel;

@Repository
public class NotificationSettingsModelRepoImpl implements NotificationSettingsModelRepo {
    @Autowired
    private NotificationSettingsRepository repository;
    @Autowired
    private NotificationSettingsMapper mapper;

    @Override
    public NotificationSettingsModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public void save(NotificationSettingsModel n) {
        repository.save(mapper.toEntity(n));
    }
}
