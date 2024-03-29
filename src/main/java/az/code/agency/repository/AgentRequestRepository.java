package az.code.agency.repository;

import az.code.agency.entity.AgentRequest;
import az.code.agency.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRequestRepository extends JpaRepository<AgentRequest, Long> {

    AgentRequest findByRequest(Request request);

    AgentRequest findByRequestId(Long requestId);
}
