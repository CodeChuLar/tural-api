package az.code.agency.dto.request;

import lombok.Data;

@Data
public class OfferRequest {
    String price;
    String dateRange;
    String additionalInfo;
}
