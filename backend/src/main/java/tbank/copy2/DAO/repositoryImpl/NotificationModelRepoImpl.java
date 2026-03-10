package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationModelMapper;
import tbank.copy2.repository.entity.Notification;
import tbank.copy2.repository.repository.NotificationRepository;
import tbank.copy2.service.model.NotificationModel;
import tbank.copy2.service.repository.NotificationModelRepository;

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
    public List<NotificationModel> findAllForRemind(LocalDateTime now) {
        List<Notification> entities = repository.findAllToRemind(true, now);
        return entities.stream().map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public NotificationModel save(NotificationModel n) {
        Notification saved = repository.save(mapper.toEntity(n));
        return mapper.toModel(saved);
    }

    @Override
    public NotificationModel findByUserIdAndTestId(Long userId, Long testId) {
        Notification n = repository.findByUserIdAndTestId(userId, testId);
        return mapper.toModel(n);
    }

    @Override
    public List<NotificationModel> findEnabledByUserId(Long userId) {
        List<Notification> l = repository.findAllEnabledByUserId(userId);
        return l.stream().map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public void deleteByUserIdAndTestId(Long userId, Long testId) {
        repository.deleteByUserIdAndTestId(userId, testId);
    }
}
