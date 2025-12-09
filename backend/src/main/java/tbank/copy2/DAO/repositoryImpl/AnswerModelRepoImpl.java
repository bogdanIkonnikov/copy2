package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.AnswerModelMapper;
import tbank.copy2.DAO.repository.AnswerModelRepository;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.repository.AnswerRepository;
import tbank.copy2.service.model.AnswerModel;

import java.util.List;

@Repository
public class AnswerModelRepoImpl implements AnswerModelRepository {
    @Autowired
    AnswerRepository repository;
    @Autowired
    private AnswerModelMapper mapper;

    @Override
    public List<AnswerModel> findAllByQuestionId(Long id) {
        List<Answer> answers = repository.findAllByQuestionId(id);
        List<AnswerModel> answerModels = answers.stream().map(a -> mapper.toModel(a)).toList();
        return answerModels;
    }

    @Override
    public AnswerModel save(AnswerModel answerModel) {
        Answer answer = repository.save(mapper.toEntity(answerModel));
        return mapper.toModel(answer);
    }
}
