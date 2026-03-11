package tbank.copy2.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.UserStatistic;

@Repository
public interface UserStatisticRepository extends JpaRepository<UserStatistic, Long> {
}
