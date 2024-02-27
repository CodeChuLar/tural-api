package az.code.agency.service;

import az.code.agency.entity.Offer;
import az.code.agency.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    @Override
    public Offer sendOfferToAgent(Long agentId, Offer offer, long sessionId) {
        return offerRepository.save(offer);
    }
}
