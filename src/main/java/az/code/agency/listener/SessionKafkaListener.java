package az.code.agency.listener;

import az.code.agency.dto.SessionDTO;
import az.code.agency.service.SessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SessionKafkaListener {

    private final SessionService sessionService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "session-new-topic", groupId = "telegram-bot")
    public void listen(ConsumerRecord<String, String> record) {
        try {
            log.info("Received {}", record);

            // Deserialize the JSON payload directly into SessionDTO object
            SessionDTO sessionDTO = objectMapper.readValue(record.value(), SessionDTO.class);

            // Save the session using the SessionService
            sessionService.saveSession(sessionDTO);

            log.info("Session saved: {}", sessionDTO);
        } catch (JsonProcessingException e) {
            log.error("Error processing session message: {}", e.getMessage());
        }
    }
}
