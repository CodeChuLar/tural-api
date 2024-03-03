package az.code.agency.controller;

import az.code.agency.entity.AgentRequest;
import az.code.agency.service.AgentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/agent-requests")
@RequiredArgsConstructor
@CrossOrigin
public class AgentRequestController {

    private final AgentRequestService service;

    @GetMapping("/{requestId}")
    public ResponseEntity<AgentRequest> findByRequest(@PathVariable Long requestId) {
        return new ResponseEntity<>(service.findByRequestId(requestId), HttpStatus.OK);
    }
}
