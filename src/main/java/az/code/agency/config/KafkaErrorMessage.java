package az.code.agency.config;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class KafkaErrorMessage<T> {
    T data;
    String error;
}
