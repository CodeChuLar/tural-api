package az.code.agency.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.springframework.http.HttpHeaders.ACCEPT_LANGUAGE;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(EmailExistException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailExistException(EmailExistException ex,
                                                                      WebRequest req) {

        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Email already exist")
                .build());
    }

    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAgentNotFoundException(AgentNotFoundException ex,
                                                                         WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(400).body(ErrorResponseDto.builder()
                .status(400)
                .title("Exception")
                .details("Agent not found")
                .build());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(InvalidPasswordException ex,
                                                                           WebRequest req) {
        ex.printStackTrace();

        return ResponseEntity.status(404).body(ErrorResponseDto.builder()
                .status(404)
                .title("Exception")
                .details("Invalid password")
                .build());
    }
}
