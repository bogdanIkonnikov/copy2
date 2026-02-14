package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.UserAnswerModelMapper;
import tbank.copy2.service.repository.UserAnswerModelRepository;
import tbank.copy2.repository.entity.UserAnswer;
import tbank.copy2.repository.repository.UserAnswerRepository;
import tbank.copy2.service.model.UserAnswerModel;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserAnsModelRepoImpl implements UserAnswerModelRepository {
    @Autowired
    private UserAnswerModelMapper mapper;
    @Autowired
    private UserAnswerRepository repository;

    @Override
    public UserAnswerModel save(UserAnswerModel userAnswerModel) {
        UserAnswer userAnswer = mapper.toEntity(userAnswerModel);
        return mapper.toModel(repository.save(userAnswer));
    }

    @Override
    public List<UserAnswerModel> findUserAnswerModelsBySessionId(Long id) {
        List<UserAnswer> userAnswers = repository.findUserAnswersBySession_id(id);
        return userAnswers.stream().map(mapper::toModel).collect(Collectors.toList());
    }
}
