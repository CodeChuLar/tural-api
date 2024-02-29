package az.code.agency.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferResponse {
    long id;
    String price;
    String dateRange;
    String additionalInfo;
    private AgentResponse agent;
    RequestResponse request;
}
