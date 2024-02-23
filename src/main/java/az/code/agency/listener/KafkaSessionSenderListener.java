package az.code.agency.listener;

import az.code.agency.dto.request.SessionRequestDTO;
import az.code.agency.entity.Session;
import az.code.agency.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaSessionSenderListener {

    private final SessionService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = {"session-front-topic"}, containerFactory = "kafkaListenerContainerFactory")
    public void processSessionAnswer(ConsumerRecord<String, String> record) {
        log.info("Received session answer: {}", record.value());

        try {
            Session session = objectMapper.readValue(record.value(), Session.class);
            SessionRequestDTO requestDTO = mapSessionToRequestDTO(session);
            service.createSession(requestDTO);
        } catch (IOException e) {
            log.error("Error parsing session answer: {}", e.getMessage());
        }
    }

    private SessionRequestDTO mapSessionToRequestDTO(Session session) {
        // Split the answers string into key-value pairs
        String[] keyValuePairs = session.getAnswers().split(",");

        // Create a map to store the answers
        Map<String, String> answersMap = new HashMap<>();

        // Iterate through the key-value pairs and add them to the map
        for (String pair : keyValuePairs) {
            String[] parts = pair.split(":");
            if (parts.length == 2) {
                // Remove extra quotes and trim spaces from keys and values
                String key = parts[0].replaceAll("\"", "").trim();
                String value = parts[1].replaceAll("\"", "").trim();
                answersMap.put(key, value);
            }
        }

        return SessionRequestDTO.builder()
                .client(session.getClient())
                .active(session.isActive())
                .answers(answersMap)
                .build();
    }


}