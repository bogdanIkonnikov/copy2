package tbank.copy2.repository.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.Test;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findAll(Pageable pageable);
    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Test> findById(Long testId);
    Page<Test> findByNameContainingIgnoreCase(String name, Pageable pageable);
    void deleteAllByVisibleAndUser_Id(boolean visible, Long userId);
}
