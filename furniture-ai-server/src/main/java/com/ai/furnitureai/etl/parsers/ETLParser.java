package com.ai.furnitureai.etl.parsers;

import org.springframework.ai.document.Document;

import java.util.List;

public interface ETLParser {

    List<Document> readFile(String url) throws Exception;

    boolean canParse(String url);

    String getName();
}
