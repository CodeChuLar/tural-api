package az.code.agency.listener;

import az.code.agency.dto.ClientDTO;
import az.code.agency.entity.Client;
import az.code.agency.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientMessageListener {

    private final ObjectMapper objectMapper;
    private final ClientService clientService;

    @Autowired
    public ClientMessageListener(ObjectMapper objectMapper, ClientService clientService) {
        this.objectMapper = objectMapper;
        this.clientService = clientService;
    }

    @KafkaListener(topics = "client-new-topic", groupId = "telegram-bot")
    public void listen(ConsumerRecord<String, String> record) {
        String message = record.value();
        try {
            Client client = deserializeClient(message);
            processClient(client);
            log.info("Received client: {}", client);
        } catch (Exception e) {
            log.error("Error processing client message: {}", e.getMessage());
        }
    }

    private Client deserializeClient(String message) throws Exception {
        return objectMapper.readValue(message, Client.class);
    }

    private void processClient(Client client) {
        // Convert Client entity to DTO
        ClientDTO clientDTO = convertToDTO(client);
        // Save client DTO using service
        clientService.saveClient(clientDTO);
        log.info("Saved client to database: {}", clientDTO);
    }

    private ClientDTO convertToDTO(Client client) {
        return ClientDTO.builder()
                .clientId(client.getClientId())
                .chatId(client.getChatId())
                .fullName(client.getFullName())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }
}