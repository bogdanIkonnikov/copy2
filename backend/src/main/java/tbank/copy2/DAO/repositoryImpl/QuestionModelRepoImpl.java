package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.QuestionModelMapper;
import tbank.copy2.domain.repository.QuestionModelRepository;
import tbank.copy2.infrastructure.persistence.entity.Question;
import tbank.copy2.infrastructure.persistence.repository.QuestionRepository;
import tbank.copy2.domain.model.QuestionModel;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionModelRepoImpl implements QuestionModelRepository {
    @Autowired
    private QuestionModelMapper mapper;
    @Autowired
    private QuestionRepository repository;

    @Override
    public QuestionModel save(QuestionModel questionModel) {
        Question question = repository.save(mapper.toEntity(questionModel));
        return mapper.toModel(question);
    }

    @Override
    public List<QuestionModel> findAllByTestId(Long testId) {
        List<Question> questions = repository.findAllByTestId(testId);
        return questions.stream().map(q -> mapper.toModel(q)).collect(Collectors.toList());
    }

    @Override
    public QuestionModel findById(Long id) {
        Question question = repository.findById(id).orElse(null);
        return mapper.toModel(question);
    }

    @Override
    public void flush() {
        repository.flush();
    }
}
