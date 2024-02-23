package az.code.agency.service;

import az.code.agency.dto.request.SessionRequestDTO;
import az.code.agency.entity.Session;
import az.code.agency.repository.SessionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo sessionRepo;
   private final ObjectMapper objectMapper;

    public void createSession(SessionRequestDTO requestDTO) {
        Session session = Session.builder()
                .client(requestDTO.getClient())
                .active(requestDTO.isActive())
                .registeredAt(LocalDateTime.now())
                .answers(convertAnswersToJson(requestDTO.getAnswers())) // Convert answers to JSON
                .build();

        sessionRepo.save(session);
        log.info("Session created: {}", session);
    }

    private String convertAnswersToJson(Map<String, String> answers) {
        try {
            return new ObjectMapper().writeValueAsString(answers);
        } catch (JsonProcessingException e) {
            log.error("Error converting answers to JSON: {}", e.getMessage());
            return null;
        }
    }


    //    public List<String> getAllAnswers() {
//        List<Session> sessions = sessionRepo.findAll();
//        return sessions.stream()
//                .map(Session::getAnswers)
//                .collect(Collectors.toList());
//    }


    public List<SessionRequestDTO> getAll() {
        List<Session> sessions = sessionRepo.findAll();
        return sessions.stream()
                .map(this::mapToSessionRequestDTO)
                .collect(Collectors.toList());
    }

    private SessionRequestDTO mapToSessionRequestDTO(Session session) {
        SessionRequestDTO sessionRequestDTO = new SessionRequestDTO();
        sessionRequestDTO.setClient(session.getClient());
        sessionRequestDTO.setActive(session.isActive());

        try {
            Map<String, String> answersMap = objectMapper.readValue(session.getAnswers(), new TypeReference<Map<String, String>>(){});
            sessionRequestDTO.setAnswers(answersMap);
        } catch (Exception e) {
            e.printStackTrace();
            sessionRequestDTO.setAnswers(Collections.emptyMap());
        }

        return sessionRequestDTO;
    }
}
