package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationModelMapper;
import tbank.copy2.infrastructure.persistence.repository.NotificationRepository;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class NotificationModelRepoImpl implements NotificationModelRepository {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private NotificationModelMapper mapper;

    @Override
    public List<NotificationModel> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId).stream()
                .map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public NotificationModel save(NotificationModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public List<NotificationModel> findAllBySentAtBeforeAndSent(LocalDateTime now, boolean b) {
        return repository.findAllBySentAndSentAtBefore(now, b).stream().map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public void saveAll(List<NotificationModel> notifications) {
        repository.saveAll(notifications.stream().map(n -> mapper.toEntity(n)).collect(Collectors.toList()));
    }

    @Override
    public void deleteBySettingsId(Long id) {
        repository.deleteBySettingsId(id);
    }
}
