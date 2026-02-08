package com.ai.furnitureai.controllers;

import com.ai.furnitureai.services.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path = "/api/chat")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @Operation(summary = "Chat with agent", description = "Agent can check database and do order operations")
    @GetMapping(value = "/message/{chatmessage}", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<String> respond(@PathVariable String chatmessage) {
        return Mono.just(
                agentService.chat(chatmessage)
        );
    }
}
