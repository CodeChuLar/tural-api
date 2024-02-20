package az.code.agency.controller;

import az.code.agency.entity.Agent;
import az.code.agency.entity.Offer;
import az.code.agency.service.OfferServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/offers")
@CrossOrigin
public class OfferController {
    private final OfferServiceImpl offerService;

    public OfferController(OfferServiceImpl offerService) {
        this.offerService = offerService;
    }


}
