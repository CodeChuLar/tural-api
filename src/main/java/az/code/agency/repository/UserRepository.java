package az.code.agency.repository;

import az.code.agency.entity.Agent;
import az.code.agency.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String confirmationToken);

    Boolean existsByEmail(String email);
}
