package com.ai.furnitureai.config;

import com.ai.furnitureai.etl.DocumentETL;
import com.ai.furnitureai.etl.ETLConfiguration;
import com.ai.furnitureai.etl.ETLManager;
import com.ai.furnitureai.etl.parsers.MDParser;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
public class RagConfig {

    @Bean
    public DocumentTransformer documentTransformer() {
        return new TokenTextSplitter(
                400,
                200,
                5,
                2000,
                true);
    }

    @Bean
    public ETLManager etlManager() {
        return new ETLManager(
                List.of(new MDParser()),
                false
        );
    }

    @Bean
    public ETLConfiguration etlConfiguration(
            VectorStore vectorStore,
            ETLManager etlManager,
            DocumentTransformer documentTransformer
    ) {
        return new ETLConfiguration(documentTransformer, vectorStore, etlManager);
    }

    @Bean
    public DocumentETL documentETL(ETLConfiguration etlConfiguration) {
        return new DocumentETL(etlConfiguration);
    }

}
