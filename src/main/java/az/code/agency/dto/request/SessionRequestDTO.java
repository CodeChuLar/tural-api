package az.code.agency.dto.request;

import az.code.agency.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDTO {
    private Client client;
    private boolean active;
    private List<String> answers;
}