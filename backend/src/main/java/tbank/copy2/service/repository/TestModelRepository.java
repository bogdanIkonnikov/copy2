package tbank.copy2.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tbank.copy2.service.model.TestModel;

import java.util.List;

public interface TestModelRepository {
    List<TestModel> findAll(Pageable pageable);

    List<TestModel> findAll();

    TestModel save(TestModel model);

    TestModel findById(Long id);

    Long deleteById(Long id);

    Page<TestModel> findByNameContainingIgnoreCase(String name, Pageable pageable);

    void deleteByUserIdAndVisible(Long id, boolean visible);
}
