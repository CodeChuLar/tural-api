package az.code.agency.dto.response;

import lombok.Data;

@Data
public class OfferResponse {
    long id;
    String price;
    String dateRange;
    String additionalInfo;
    private AgentResponse agent;
    RequestResponse request;
}
