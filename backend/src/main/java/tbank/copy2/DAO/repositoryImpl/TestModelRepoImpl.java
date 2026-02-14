package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.TestModelMapper;
import tbank.copy2.service.repository.TestModelRepository;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.service.model.TestModel;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TestModelRepoImpl implements TestModelRepository {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestModelMapper mapper;

    @Override
    public List<TestModel> findAll(Pageable pageable) {
        List<TestModel> models = testRepository.findAll(pageable).stream()
                .map(t -> mapper.toModel(t)).collect(Collectors.toList());
        return models;
    }

    @Override
    public List<TestModel> findAll() {
        List<TestModel> models = testRepository.findAll().stream()
                .map(t -> mapper.toModel(t)).collect(Collectors.toList());
        return models;
    }

    @Override
    public TestModel save(TestModel model) {
        Test saved = testRepository.save(mapper.toEntity(model));
        return mapper.toModel(saved);
    }

    @Override
    public TestModel findById(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        return mapper.toModel(test);
    }

    @Override
    public Long deleteById(Long id) {
        testRepository.deleteById(id);
        return id;
    }

    @Override
    public Page<TestModel> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        Page<Test> tests = testRepository.findByNameContainingIgnoreCase(name, pageable);
        return mapper.toPageModel(tests);
    }

    @Override
    public void deleteByUserIdAndVisible(Long id, boolean visible){
        testRepository.deleteAllByVisibleAndUser_Id(visible, id);
    }

}
