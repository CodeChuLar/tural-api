package az.code.agency.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestResponse {
    Long id;
    String title;
    UUID sessionId;
    LocalDateTime creationTime;
    LocalDateTime deadline;
    String fullName;
    String phoneNumber;
    boolean active;
}
