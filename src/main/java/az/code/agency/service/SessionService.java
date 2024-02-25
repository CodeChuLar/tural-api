package az.code.agency.service;

import az.code.agency.dto.ClientDTO;
import az.code.agency.dto.SessionDTO;
import az.code.agency.entity.Client;
import az.code.agency.entity.Session;
import az.code.agency.repository.SessionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo sessionRepository;
    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    public void saveSession(SessionDTO sessionDTO) throws JsonProcessingException {
        // Convert ClientDTO to Client entity
        ClientDTO clientDTO = sessionDTO.getClient();
        Client client = clientService.convertToEntity(clientDTO);

        // Clean up and parse answers
        Map<String, String> cleanedAnswers = parseAnswers(sessionDTO.getAnswers());

        // Create Session entity
        Session session = Session.builder()
                .client(client)
                .active(sessionDTO.isActive())
                .registeredAt(sessionDTO.getRegisteredAt())
                .answers(cleanedAnswers) // Store cleaned answers
                .build();

        // Save the session
        sessionRepository.save(session);
    }

    private Map<String, String> parseAnswers(String answers) throws JsonProcessingException {
        // Convert the answers JSON string to a Map<String, String>
        return objectMapper.readValue(answers, new TypeReference<Map<String,String>>(){});
    }


    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

}
