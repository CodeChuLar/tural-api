package az.code.agency.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionDto {
    private UUID sessionId;
    private ClientDto client;
    @JsonProperty("answers")
    private String answers;
    boolean active;
}
