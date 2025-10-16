package tbank.copy2.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
