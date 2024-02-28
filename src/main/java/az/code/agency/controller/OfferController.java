package az.code.agency.controller;

import az.code.agency.dto.request.OfferRequest;
import az.code.agency.entity.Offer;
import az.code.agency.exception.AgentNotFoundException;
import az.code.agency.exception.RequestNotFoundException;
import az.code.agency.service.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
@CrossOrigin
public class OfferController {

    private final OfferService offerService;

    @PostMapping
    public ResponseEntity<String> createOffer(@RequestParam Long requestId, @RequestParam Long agentId, @RequestBody OfferRequest offerRequest) {
        try {
            offerService.createOffer(requestId, agentId, offerRequest);
            return ResponseEntity.ok("Offer created successfully.");
        } catch (AgentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agent not found.");
        } catch (RequestNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the offer.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Offer>> getAll() {
        return new ResponseEntity<>(offerService.getAll(),HttpStatus.OK);
    }
}