package az.code.agency.service;

import az.code.agency.dto.request.OfferRequest;
import az.code.agency.dto.response.AgentResponse;
import az.code.agency.dto.response.OfferResponse;
import az.code.agency.dto.response.RequestResponse;
import az.code.agency.entity.Agent;
import az.code.agency.entity.AgentRequest;
import az.code.agency.entity.Request;
import az.code.agency.exception.AgentNotFoundException;
import az.code.agency.exception.RequestNotFoundException;
import az.code.agency.repository.AgentRepository;
import az.code.agency.repository.AgentRequestRepository;
import az.code.agency.repository.OfferRepository;
import az.code.agency.repository.RequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    private OfferRepository offerRepository;

    @Mock
    private AgentRepository agentRepository;

    @Mock
    private RequestRepository requestRepository;

    @Mock
    private AgentRequestRepository agentRequestRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private OfferService offerService;

    @Test
    void createOffer_Success() {
        // Arrange
        Long requestId = 1L;
        Long agentId = 1L;
        OfferRequest offerRequest = OfferRequest.builder()
                .price("100")
                .dateRange("2024-03-01 to 2024-03-15")
                .additionalInfo("Additional info")
                .build();

        Request request = Request.builder()
                .id(requestId)
                .active(true) // Ensure the request is active
                .title("Test Request")
                .build();

        Agent agent = Agent.builder()
                .id(agentId)
                .voen("Agent Voen")
                .agentName("Agent Name")
                .companyName("Agent Company")
                .build();

        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(agentRepository.findById(agentId)).thenReturn(Optional.of(agent));
        when(agentRequestRepository.findByRequest(request)).thenReturn(null);
        when(offerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        OfferResponse expectedOfferResponse = new OfferResponse();
        expectedOfferResponse.setId(0L); // Set the expected ID
        expectedOfferResponse.setPrice("100");
        expectedOfferResponse.setDateRange("2024-03-01 to 2024-03-15");
        expectedOfferResponse.setAdditionalInfo("Additional info");

        AgentResponse expectedAgentResponse = new AgentResponse();
        expectedAgentResponse.setId(null);
        expectedAgentResponse.setVoen("Agent Voen");
        expectedAgentResponse.setAgentName("Agent Name");
        expectedAgentResponse.setCompanyName("Agent Company");
        expectedOfferResponse.setAgent(expectedAgentResponse);

        RequestResponse expectedRequestResponse = new RequestResponse();
        expectedRequestResponse.setId(1L);
        expectedRequestResponse.setTitle("Test Request");
        expectedRequestResponse.setActive(true);
        expectedRequestResponse.setSessionId(null);
        expectedRequestResponse.setCreationTime(null);
        expectedRequestResponse.setDeadline(null);
        expectedRequestResponse.setFullName(null);
        expectedRequestResponse.setPhoneNumber(null);
        expectedOfferResponse.setRequest(expectedRequestResponse);

        // Act
        OfferResponse offerResponse = offerService.createOffer(requestId, agentId, offerRequest);

        // Assert
        assertEquals(expectedOfferResponse, offerResponse);

        // Verify interactions
        verify(requestRepository, times(1)).findById(requestId);
        verify(agentRepository, times(1)).findById(agentId);
        verify(agentRequestRepository, times(1)).findByRequest(request);
        verify(offerRepository, times(1)).save(any());
        verify(kafkaTemplate, times(1)).send(anyString(), any());
    }




}