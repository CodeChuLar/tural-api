package az.code.agency.controller;

import az.code.agency.exception.RequestNotFoundException;
import az.code.agency.service.ArchivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/archives")
public class ArchivingController {

    private final ArchivingService archivingService;

    @PutMapping("/request/{requestId}")
    public ResponseEntity<String> archiveRequestById(@PathVariable Long requestId) {
        try {
            archivingService.sendRequestToArchiveById(requestId);
            return ResponseEntity.ok("Request has been successfully sent to the archive.");
        } catch (RequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found with id: " + requestId);
        }
    }
}