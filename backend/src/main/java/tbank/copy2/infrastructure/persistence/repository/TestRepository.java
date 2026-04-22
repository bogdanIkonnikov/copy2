package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbank.copy2.common.enums.AccessLevel;
import tbank.copy2.common.enums.AccessMode;
import tbank.copy2.infrastructure.persistence.entity.Test;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    @Query("SELECT DISTINCT t FROM Test t " +
            "JOIN t.accesses a " +
            "WHERE a.user.id = :userId")
    List<Test> findAllTestsByUserAccess(Long userId);
    @Query("SELECT DISTINCT t FROM Test t " +
            "WHERE t.user.id = :userId " +
            "OR t.accessMode = :accessMode")
    Page<Test> findAllTestsByUserAccess(Pageable pageable, @Param("userId") Long userId, AccessMode accessMode);

    @Query("SELECT DISTINCT t FROM Test t " +
            "WHERE t.user.id != :userId " +
            "AND t.accessMode = :accessMode")
    Page<Test> findAllAlienPublicTests(Pageable pageable, @Param("userId") Long userId, AccessMode accessMode);

    @EntityGraph(attributePaths = {"questions", "questions.answers"})
    Optional<Test> findById(Long testId);
    @Query("SELECT DISTINCT t FROM Test t " +
            "LEFT JOIN t.accesses a " +
            "WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND (a.user.id = :userId OR t.accessMode = :accessMode)")
    Page<Test> findByNameContainingIgnoreCase(@Param("name") String name,
                                              @Param("userId") Long userId,
                                              Pageable pageable,
                                              AccessMode accessMode);
    void deleteAllByVisibleAndUser_Id(boolean visible, Long userId);

    @Query("SELECT COUNT(a) > 0 FROM TestAccess a " +
            "WHERE a.test.id = :testId " +
            "AND a.user.id = :userId " +
            "AND a.accessLevel = :level")
    boolean hasEditAccess(@Param("userId") Long userId, @Param("testId") Long testId, @Param("level") AccessLevel level);

    Optional<Test> findByShareToken(String shareToken);

    @Query("SELECT DISTINCT t FROM Test t " +
            "WHERE t.user.id != :userId " +
            "AND t.accessMode = :accessMode")
    List<Test> findAllAlienPublicTests(Long userId, AccessMode accessMode);
}
