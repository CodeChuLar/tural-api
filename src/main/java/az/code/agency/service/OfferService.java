package az.code.agency.service;

import az.code.agency.entity.Agent;
import az.code.agency.entity.Offer;

public interface OfferService {

    Offer sendOfferToAgent(Long agentId, Offer offer, long sessionId);
}
