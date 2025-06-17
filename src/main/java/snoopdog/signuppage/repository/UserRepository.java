package snoopdog.signuppage.repository;

import snoopdog.signuppage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // optional but useful for validation

    boolean existsByUsername(String username);
}
