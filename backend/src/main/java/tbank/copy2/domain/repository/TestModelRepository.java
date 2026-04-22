package tbank.copy2.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tbank.copy2.domain.model.TestModel;

import java.util.List;

public interface TestModelRepository {
    List<TestModel> findAllByUserId(Pageable pageable, Long userId);

    List<TestModel> findAllByUserId(Long userId);

    List<TestModel> findAll();

    TestModel save(TestModel model);

    TestModel findById(Long id);

    Long deleteById(Long id);

    Page<TestModel> findByNameContainingIgnoreCase(String name, Long userId, Pageable pageable);

    void deleteByUserIdAndVisible(Long id, boolean visible);

    boolean hasEditAccess(Long userId, Long testId);

    List<TestModel> findAllAlienPublicTests(Pageable pageable, Long userId);

    List<TestModel> findAllAlienPublicTests(Long userId);

    TestModel findByShareToken(String shareToken);

}
