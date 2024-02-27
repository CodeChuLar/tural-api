package az.code.agency.listener;

import az.code.agency.dto.SessionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SessionKafkaListener {

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "session-new-topic", groupId = "telegram-bot")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Received {}", record);
    }
}
