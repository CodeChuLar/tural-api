package az.code.agency.service;

import az.code.agency.entity.Offer;
import az.code.agency.repository.OfferRepository;
import az.code.agency.repository.SessionRepository;
import org.springframework.stereotype.Service;

@Service
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final SessionRepository sessionRepository;

    public OfferServiceImpl(OfferRepository offerRepository, SessionRepository sessionRepository) {
        this.offerRepository = offerRepository;
        this.sessionRepository = sessionRepository;
    }


    @Override
    public Offer sendOfferToAgent(Long agentId, Offer offer, long sessionId) {
        return offerRepository.save(offer);
    }
}
