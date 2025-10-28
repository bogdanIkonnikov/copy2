package tbank.copy2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
