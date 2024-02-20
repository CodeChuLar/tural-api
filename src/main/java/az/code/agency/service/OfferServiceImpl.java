package az.code.agency.service;

import az.code.agency.entity.Offer;
import az.code.agency.repository.OfferRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }


}
