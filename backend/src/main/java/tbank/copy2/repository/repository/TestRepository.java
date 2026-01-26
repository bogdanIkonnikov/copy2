package tbank.copy2.repository.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Test> findById(Long testId);
    List<Test> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
