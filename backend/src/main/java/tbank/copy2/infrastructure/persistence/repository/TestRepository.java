package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findAllByUserId(Long userId);
    Page<Test> findAllByUserId(Pageable pageable, Long userId);
    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Test> findById(Long testId);
    Page<Test> findByNameContainingIgnoreCase(String name, Pageable pageable);
    void deleteAllByVisibleAndUser_Id(boolean visible, Long userId);
}
