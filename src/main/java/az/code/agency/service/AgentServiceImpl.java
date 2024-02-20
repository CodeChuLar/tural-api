package az.code.agency.service;

import az.code.agency.entity.Agent;
import az.code.agency.entity.Offer;
import az.code.agency.exception.AgentNotFoundException;
import az.code.agency.exception.ErrorCodes;
import az.code.agency.repository.AgentRepository;
import az.code.agency.repository.OfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final OfferRepository offerRepository;


    @Override
    public void sendOffer(long agentId, Offer offer) {
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException(ErrorCodes.AGENT_NOT_FOUND));
        offer.setAgent(agent);
        offerRepository.save(offer);
    }


}
