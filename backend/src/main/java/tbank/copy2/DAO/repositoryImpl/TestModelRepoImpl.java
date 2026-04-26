package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.TestModelMapper;
import tbank.copy2.common.enums.AccessLevel;
import tbank.copy2.common.enums.AccessMode;
import tbank.copy2.domain.repository.TestModelRepository;
import tbank.copy2.infrastructure.persistence.entity.Test;
import tbank.copy2.infrastructure.persistence.repository.TestRepository;
import tbank.copy2.domain.model.TestModel;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TestModelRepoImpl implements TestModelRepository {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestModelMapper mapper;

    @Override
    public List<TestModel> findAllByUserId(Pageable pageable, Long userId) {
        List<TestModel> models = testRepository.findAllTestsByUserId(pageable, userId).stream()
                .map(t -> mapper.toModel(t)).collect(Collectors.toList());
        return models;
    }

    @Override
    public List<TestModel> findAllByUserId(Long userId) {
        return testRepository.findAllTestsByUserId(userId).stream().map(t -> mapper.toModel(t)).collect(Collectors.toList());
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
    public Page<TestModel> findByNameContainingIgnoreCase(String name, Long userId, Pageable pageable) {
        Page<Test> tests = testRepository.findByNameContainingIgnoreCase(name, userId ,pageable, AccessMode.PUBLIC);
        return mapper.toPageModel(tests);
    }

    @Override
    public void deleteByUserIdAndVisible(Long id, boolean visible){
        testRepository.deleteAllByVisibleAndUser_Id(visible, id);
    }

    @Override
    public boolean hasEditAccess(Long userId, Long testId) {
        return testRepository.hasEditAccess(userId, testId, AccessLevel.WRITE);
    }

    @Override
    public List<TestModel> findAllPublicTests(Pageable pageable, Long userId) {
        return testRepository.findAllPublicTests(pageable, userId, AccessMode.PUBLIC).stream().map(t -> mapper.toModel(t)).collect(Collectors.toList());
    }

    @Override
    public List<TestModel> findAllPublicTests(Long userId) {
        return testRepository.findAllPublicTests(userId, AccessMode.PUBLIC).stream().map(t -> mapper.toModel(t)).collect(Collectors.toList());
    }

    @Override
    public TestModel findByShareToken(String shareToken) {
        return mapper.toModel(testRepository.findByShareToken(shareToken).orElse(null));
    }

    @Override
    public List<TestModel> findByNamePublicTests(String keyword, Long userId, Pageable pageable) {
        return testRepository.findByNameAlienPublicTests(keyword, userId, pageable, AccessMode.PUBLIC)
                .stream().map(t -> mapper.toModel(t)).collect(Collectors.toList());
    }
}
