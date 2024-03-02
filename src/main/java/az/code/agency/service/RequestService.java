package az.code.agency.service;

import az.code.agency.dto.ClientDto;
import az.code.agency.dto.SessionDto;
import az.code.agency.entity.Request;
import az.code.agency.repository.RequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;
    private final ObjectMapper objectMapper;


    @Transactional
    public void saveSessionToRequest(SessionDto sessionDto) {
        Request request = new Request();
        request.setSessionId(sessionDto.getSessionId());
        request.setCreationTime(LocalDateTime.now());
        request.setActive(sessionDto.isActive());

        ClientDto clientDto = sessionDto.getClient();
        if (clientDto != null) {
            request.setFullName(clientDto.getFullName());
            request.setPhoneNumber(clientDto.getPhoneNumber());
        } else {
            log.error("Client information not found in SessionDto");
        }

        try {
            String answersJson = sessionDto.getAnswers();
            @SuppressWarnings("unchecked")
            Map<String, Object> answersMap = objectMapper.readValue(answersJson, Map.class);
            request.setAnswers(answersMap);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
        }

        requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }
}

