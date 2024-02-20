package az.code.agency.service;

import az.code.agency.entity.Offer;

public interface AgentService {

    void sendOffer(long agentId, Offer offer);
}
