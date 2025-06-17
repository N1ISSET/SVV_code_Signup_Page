package snoopdog.signuppage.repository;

import org.springframework.data.repository.CrudRepository;
import snoopdog.signuppage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // optional but useful for validation

    boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
