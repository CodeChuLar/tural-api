package az.code.agency.controller;

import az.code.agency.dto.request.SessionRequestDTO;
import az.code.agency.entity.Session;
import az.code.agency.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<Void> createSession(@RequestBody SessionRequestDTO requestDTO) {
        sessionService.createSession(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<SessionRequestDTO>> getAll() {
        return new ResponseEntity<>(sessionService.getAll(), HttpStatus.OK);
    }
}