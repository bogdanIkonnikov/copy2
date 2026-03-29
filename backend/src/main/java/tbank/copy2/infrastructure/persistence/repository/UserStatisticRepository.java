package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.UserStatistic;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {
}
