package az.code.agency.service;

import az.code.agency.dto.ClientDto;
import az.code.agency.dto.OfferDto;
import az.code.agency.dto.SessionDto;
import az.code.agency.dto.request.OfferRequest;
import az.code.agency.dto.response.AgentResponse;
import az.code.agency.dto.response.OfferResponse;
import az.code.agency.dto.response.RequestResponse;
import az.code.agency.entity.*;
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


    public OfferResponse createOffer(Long requestId, Long agentId, OfferRequest offerRequest) {
        LocalDateTime currentTime = LocalDateTime.now();

//        if (!isWorkingHours(currentTime)) {
//            throw new IllegalStateException("Now is not working time.");
//        }

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException(ErrorCodes.AGENT_NOT_FOUND));

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException(ErrorCodes.REQUEST_NOT_FOUND));

        AgentRequest agentRequest = agentRequestRepository.findByRequest(request);


        if (agentRequest == null) {
            agentRequest = new AgentRequest();
            agentRequest.setStatus(Status.OFFERED);
            agentRequest.setRequest(request);
            agentRequest.setAgent(agent);
            agentRequestRepository.save(agentRequest);
        } else if (agentRequest.getStatus() != Status.PENDING) {
            log.error("Cannot create offer for a request as it can be offered or achieved.");
        }

        if (!request.isActive()) {
            throw new IllegalStateException("Cannot create offer for an expired request.");
        }

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

        // Populate OfferResponse with AgentResponse and RequestResponse
        OfferResponse offerResponse = new OfferResponse();
        offerResponse.setId(offer.getId());
        offerResponse.setPrice(offer.getPrice());
        offerResponse.setDateRange(offer.getDateRange());
        offerResponse.setAdditionalInfo(offer.getAdditionalInfo());

        AgentResponse agentResponse = new AgentResponse();
        agentResponse.setVoen(agent.getVoen());
        agentResponse.setAgentName(agent.getAgentName());
        agentResponse.setCompanyName(agent.getCompanyName());
        offerResponse.setAgent(agentResponse);

        RequestResponse requestResponse = new RequestResponse();
        requestResponse.setId(request.getId());
        requestResponse.setTitle(request.getTitle());
        requestResponse.setCreationTime(request.getCreationTime());
        requestResponse.setDeadline(request.getDeadline());
        requestResponse.setFullName(request.getFullName());
        requestResponse.setPhoneNumber(request.getPhoneNumber());
        requestResponse.setActive(request.isActive());
        offerResponse.setRequest(requestResponse);

        return offerResponse;
    }


    private void sendOffer(Offer offer) {
        try {
            OfferDto offerDto = convertToDto(offer);
            kafkaTemplate.send("offer-topic", offerDto);
            log.info("Offer sent successfully to another application.");
        } catch (Exception e) {
            log.error("Error sending offer to another application: {}", e.getMessage());
        }
    }

    private OfferDto convertToDto(Offer offer) {
        return OfferDto.builder()
                .offerId(offer.getId())
                .sessionId(offer.getRequest().getSessionId())
                .price(offer.getPrice())
                .dateRange(offer.getDateRange())
                .additionalInfo(offer.getAdditionalInfo())
                .companyName(offer.getAgent().getCompanyName())
                .build();
    }


    private boolean isWorkingHours(LocalDateTime currentTime) {
        return !currentTime.isBefore(WORK_START_TIME) && !currentTime.isAfter(WORK_END_TIME);
    }

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }


    public Offer findById(long id) {
        return offerRepository.findById(id).orElse(null);
    }
}
