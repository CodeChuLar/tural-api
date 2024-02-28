package az.code.agency.repository;

import az.code.agency.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findBySessionId(UUID sessionId);
}
