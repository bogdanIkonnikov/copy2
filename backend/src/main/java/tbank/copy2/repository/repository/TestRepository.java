package tbank.copy2.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

}
