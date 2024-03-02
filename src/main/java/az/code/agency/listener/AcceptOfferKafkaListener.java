package az.code.agency.listener;

import az.code.agency.dto.ClientDto;
import az.code.agency.entity.AgentRequest;
import az.code.agency.entity.Offer;
import az.code.agency.entity.Request;
import az.code.agency.entity.Status;
import az.code.agency.service.AgentRequestService;
import az.code.agency.service.OfferService;
import az.code.agency.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class AcceptOfferKafkaListener {

    private final AgentRequestService service;
    private static final Pattern OFFER_ID_PATTERN = Pattern.compile("offerId: (\\d+)");

    @KafkaListener(topics = "accept-offer-topic", groupId = "telegram-bot")
    public void listen(ConsumerRecord<String, String> record) {
        String messageValue = record.value();

        Matcher matcher = OFFER_ID_PATTERN.matcher(messageValue);
        if (matcher.find()) {
            long offerId = Long.parseLong(matcher.group(1));
            log.info("Offer received {} ", messageValue);

            service.update(offerId);
        }
    }
}

