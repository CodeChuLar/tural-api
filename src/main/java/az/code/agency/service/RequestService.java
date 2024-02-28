package az.code.agency.service;

import az.code.agency.dto.ClientDto;
import az.code.agency.dto.SessionDto;
import az.code.agency.entity.Request;
import az.code.agency.repository.RequestRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;
    private final ObjectMapper objectMapper;
    private final Request request = new Request();

    @Transactional
    public void saveSessionToRequest(SessionDto sessionDto) {
        request.setSessionId(sessionDto.getSessionId());
        request.setCreationTime(LocalDateTime.now());

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

    @Transactional
    public void saveClientToRequest(ClientDto clientDto) {
        request.setFullName(clientDto.getFullName());
        request.setPhoneNumber(clientDto.getPhoneNumber());

        requestRepository.save(request);
    }

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }
}