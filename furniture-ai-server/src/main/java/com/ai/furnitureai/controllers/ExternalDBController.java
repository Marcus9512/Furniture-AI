package com.ai.furnitureai.controllers;

import com.ai.furnitureai.services.ExternalDBService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/db")
public class ExternalDBController {

    private final ExternalDBService externalDBService;

    public ExternalDBController(ExternalDBService externalDBService) {
        this.externalDBService = externalDBService;
    }

    @Operation(summary = "Performs a smilarity search on database", description = "Returns a string containing similarity score and topK results")
    @GetMapping(value = "/search")
    public Mono<String> getSimilarityResults(@RequestParam String query,
                                             @RequestParam double similarityThreshold,
                                             @RequestParam int topK) {
        StringBuilder results = new StringBuilder();
        externalDBService.doSimilaritySearch(query, similarityThreshold, topK)
                .forEach(d -> {
                    results.append("\n")
                            .append("id=")
                            .append(d.getId())
                            .append(" ")
                            .append("score=")
                            .append(d.getScore())
                            .append(" ")
                            .append("MetaData=")
                            .append(d.getMetadata())
                            .append("\n\n")
                            .append(d.getText());
                });

        return Mono.just(results.toString());
    }
}
