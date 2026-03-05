package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.NotificationMapper;
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
    private NotificationMapper mapper;

    @Override
    public List<NotificationModel> findAllForRemind(LocalDateTime now) {
        List<Notification> entities = repository.findAllByIsEnabledAndNext_sent_atIsBefore(true, now);
        return entities.stream().map(n -> mapper.toModel(n)).collect(Collectors.toList());
    }

    @Override
    public void save(NotificationModel n) {
        repository.save(mapper.toEntity(n));
    }
}
