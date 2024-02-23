package az.code.agency.dto.request;

import az.code.agency.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDTO {
    private Client client;
    private boolean active;
    private Map<String,String> answers;
}