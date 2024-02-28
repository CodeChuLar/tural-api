package az.code.agency.controller;

import az.code.agency.entity.Request;
import az.code.agency.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return new ResponseEntity<>(requestService.getAllRequests(), HttpStatus.OK);
    }
}
