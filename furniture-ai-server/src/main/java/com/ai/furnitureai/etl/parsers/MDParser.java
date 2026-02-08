package com.ai.furnitureai.etl.parsers;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.core.io.FileSystemResource;


import java.util.List;

public class MDParser implements ETLParser {

    private MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
            .withHorizontalRuleCreateDocument(true)
            .withIncludeCodeBlock(false)
            .withIncludeBlockquote(false)
            .build();

    @Override
    public List<Document> readFile(String url) throws Exception {
        return new MarkdownDocumentReader(
                new FileSystemResource(url),
                config
        ).read();
    }

    @Override
    public boolean canParse(String url) {
        return url.endsWith(".md");
    }

    @Override
    public String getName() {
        return "Markdown Parser";
    }
}
