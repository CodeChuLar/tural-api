package az.code.agency.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SessionUtil {
    Long chatId;
    Language lang;
    Long currentQuestion;
    List<String> answers;
}
