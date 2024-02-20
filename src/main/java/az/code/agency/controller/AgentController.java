package az.code.agency.controller;

import az.code.agency.entity.Offer;
import az.code.agency.service.AgentServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/agents")
@CrossOrigin
public class AgentController {
    private final AgentServiceImpl agentServiceImpl;

    public AgentController(AgentServiceImpl agentServiceImpl) {
        this.agentServiceImpl = agentServiceImpl;
    }




}
