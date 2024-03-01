package az.code.agency.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class OfferDto {
    private UUID sessionId;
    private String price;
    private String dateRange;
    private String additionalInfo;
}
