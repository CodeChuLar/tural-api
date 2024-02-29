package az.code.agency.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class OfferRequest {
    String price;
    String dateRange;
    String additionalInfo;
}
