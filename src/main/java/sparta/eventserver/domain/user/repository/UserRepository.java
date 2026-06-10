package sparta.eventserver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.eventserver.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
