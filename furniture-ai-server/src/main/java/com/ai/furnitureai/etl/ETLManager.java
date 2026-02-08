package com.ai.furnitureai.etl;

import com.ai.furnitureai.etl.parsers.ETLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


public class ETLManager {

    private final Logger LOGGER = LoggerFactory.getLogger(ETLManager.class);

    private List<ETLParser> configuredParsers;
    private boolean allowSameFileParsing;

    public ETLManager(List<ETLParser> configuredParsers, boolean allowSameFileParsing) {
        this.configuredParsers = configuredParsers;
        this.allowSameFileParsing = allowSameFileParsing;
    }

    public List<Document> parseUrl(String url) {
        List<Document> documents = new LinkedList<>();

        for (ETLParser parser : configuredParsers) {
            if (!parser.canParse(url)) {
                continue;
            }
            tryReadFile(parser, url).ifPresent(documents::addAll);
            if (!allowSameFileParsing) {
                break;
            }
        }
        return documents;
    }

    private Optional<List<Document>> tryReadFile(ETLParser parser, String url) {

        try {
            return Optional.of(parser.readFile(url));
        } catch (Exception e) {
            LOGGER.warn("Failed to read url={}, with parser={}", url, parser.getName());

        }
        return Optional.empty();
    }
}
