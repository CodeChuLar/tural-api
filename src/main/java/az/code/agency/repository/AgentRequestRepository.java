package az.code.agency.repository;

import az.code.agency.entity.AgentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRequestRepository extends JpaRepository<AgentRequest, Long> {
}
