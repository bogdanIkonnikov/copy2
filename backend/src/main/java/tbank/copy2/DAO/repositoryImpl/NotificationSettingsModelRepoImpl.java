package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationSettingsModelMapper;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.domain.repository.NotificationSettingsModelRepository;
import tbank.copy2.infrastructure.persistence.repository.NotificationSettingsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotificationSettingsModelRepoImpl implements NotificationSettingsModelRepository {
    @Autowired
    private NotificationSettingsRepository repository;
    @Autowired
    private NotificationSettingsModelMapper mapper;

    @Override
    public List<NotificationSettingsModel> getAllNotificationSettings(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public NotificationSettingsModel save(NotificationSettingsModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public boolean existsByUserIdAndTestId(Long userId, Long testId) {
        return repository.existsByUserIdAndTestId(userId, testId);
    }

    @Override
    public void deleteByUserIdAndTestId(Long userId, Long testId) {
        repository.deleteByUserIdAndTestId(userId, testId);
    }

    @Override
    public List<Long> findSettingsIdsWhereAllSent() {
        return repository.findSettingsIdsWhereAllSent();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
