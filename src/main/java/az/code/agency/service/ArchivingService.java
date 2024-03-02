package az.code.agency.service;

import az.code.agency.entity.AgentRequest;
import az.code.agency.entity.Request;
import az.code.agency.entity.Status;
import az.code.agency.exception.ErrorCodes;
import az.code.agency.exception.RequestNotFoundException;
import az.code.agency.repository.AgentRequestRepository;
import az.code.agency.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchivingService {

    private final RequestRepository requestRepository;
    private final AgentRequestRepository agentRequestRepository;

    @Transactional
    public void sendRequestToArchiveById(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(ErrorCodes.REQUEST_NOT_FOUND));

        request.setActive(false);
        requestRepository.save(request);

        // Find and update the associated agent request status to archived
        AgentRequest agentRequest = agentRequestRepository.findByRequest(request);
        if (agentRequest != null) {
            agentRequest.setStatus(Status.ARCHIVED);
            agentRequestRepository.save(agentRequest);
        }
    }
}