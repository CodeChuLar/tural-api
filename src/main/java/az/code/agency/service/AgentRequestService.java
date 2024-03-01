package az.code.agency.service;

import az.code.agency.entity.AgentRequest;
import az.code.agency.repository.AgentRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentRequestService {
    private final AgentRequestRepository agentRequestRepository;

    public AgentRequest findByRequestId(Long requestId) {
        return agentRequestRepository.findByRequestId(requestId);
    }
}
