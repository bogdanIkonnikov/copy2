package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationModelMapper;
import tbank.copy2.common.enums.NotificationStatus;
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
    public NotificationModel save(NotificationModel model) {
        return mapper.toModel(repository.save(mapper.toEntity(model)));
    }

    @Override
    public List<NotificationModel> findAllByUserId(Long userId) {
        return List.of();
    }

    @Override
    public List<NotificationModel> findAllBySentAtBeforeAndSent(LocalDateTime now, boolean b) {
        return List.of();
    }

    @Override
    public boolean updateIfVersionMatches(Long id, int version) {
        return false;
    }

    @Override
    public List<NotificationModel> findAllToProcess(LocalDateTime now) {
        return repository.findAllToProcess(now).stream().map(mapper::toModel).collect(Collectors.toList());
    }

    @Override
    public int updateStatus(Long id, int version, NotificationStatus notificationStatus) {
        return repository.updateStatus(id, version, notificationStatus);
    }

    @Override
    public void updateStatusFinal(Long id, NotificationStatus notificationStatus, String err) {
        repository.updateStatusFinal(id, notificationStatus, err);
    }
}
