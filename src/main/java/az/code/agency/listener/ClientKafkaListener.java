package az.code.agency.listener;

import az.code.agency.dto.ClientDto;
import az.code.agency.dto.SessionDto;
import az.code.agency.service.RequestService;
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
public class ClientKafkaListener {

    private final ObjectMapper objectMapper;
    private final RequestService requestService;

    @KafkaListener(topics = "client-new-topic", groupId = "telegram-bot")
    public void listen(ConsumerRecord<String, String> record) {
        log.info("Client Received {}", record.value());

        try {
            ClientDto clientDto = objectMapper.readValue(record.value(), ClientDto.class);
            requestService.saveClientToRequest(clientDto);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON: {}", e.getMessage());
        }
    }
}
