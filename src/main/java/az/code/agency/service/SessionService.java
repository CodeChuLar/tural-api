package az.code.agency.service;

import az.code.agency.dto.request.SessionRequestDTO;
import az.code.agency.entity.Session;
import az.code.agency.repository.SessionRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo sessionRepo;

    public void createSession(SessionRequestDTO requestDTO) {
        Session session = Session.builder()
                .client(requestDTO.getClient())
                .active(requestDTO.isActive())
                .registeredAt(LocalDateTime.now())
                .answers(convertAnswersToString(requestDTO.getAnswers()))
                .build();

        sessionRepo.save(session);
        log.info("Session created: {}", session);
    }

    private String convertAnswersToString(List<String> answers) {
        try {
            return new ObjectMapper().writeValueAsString(answers);
        } catch (JsonProcessingException e) {
            log.error("Error converting answers to string: {}", e.getMessage());
            return null;
        }
    }

    public List<String> getAllAnswers() {
        List<Session> sessions = sessionRepo.findAll();
        return sessions.stream()
                .map(Session::getAnswers)
                .collect(Collectors.toList());
    }
}
