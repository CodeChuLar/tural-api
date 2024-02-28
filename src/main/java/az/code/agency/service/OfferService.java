package az.code.agency.service;

import az.code.agency.dto.ClientDto;
import az.code.agency.dto.SessionDto;
import az.code.agency.dto.request.OfferRequest;
import az.code.agency.entity.Agent;
import az.code.agency.entity.Offer;
import az.code.agency.entity.Request;
import az.code.agency.exception.AgentNotFoundException;
import az.code.agency.exception.ErrorCodes;
import az.code.agency.exception.RequestNotFoundException;
import az.code.agency.repository.AgentRepository;
import az.code.agency.repository.AgentRequestRepository;
import az.code.agency.repository.OfferRepository;
import az.code.agency.repository.RequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferService {

    private final OfferRepository offerRepository;
    private final LocalDateTime WORK_START_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0));
    private final LocalDateTime WORK_END_TIME = LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0));
    private final AgentRepository agentRepository;
    private final RequestRepository requestRepository;
    private final AgentRequestRepository agentRequestRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public void createOffer(Long requestId, Long agentId, OfferRequest offerRequest) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (!isWorkingHours(currentTime)) {
            log.warn("Offer cannot be sent outside working hours.");
            return;
        }

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException(ErrorCodes.AGENT_NOT_FOUND));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(ErrorCodes.REQUEST_NOT_FOUND));

        if (request.isActive()) {
            Offer offer = Offer.builder()
                    .price(offerRequest.getPrice())
                    .dateRange(offerRequest.getDateRange())
                    .additionalInfo(offerRequest.getAdditionalInfo())
                    .agent(agent)
                    .request(request)
                    .build();

            offerRepository.save(offer);
            log.info("Offer created successfully for client {}.", request.getFullName());

            sendOffer(offer);
        } else {
            throw new IllegalStateException("Cannot create offer for an expired request.");
        }

    }

    private void sendOffer(Offer offer) {
        try {
            kafkaTemplate.send("offer-topic", offer);
            log.info("Offer sent successfully to another application.");
        } catch (Exception e) {
            log.error("Error sending offer to another application: {}", e.getMessage());
        }
    }


    private boolean isWorkingHours(LocalDateTime currentTime) {
        return !currentTime.isBefore(WORK_START_TIME) && !currentTime.isAfter(WORK_END_TIME);
    }

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }
}
