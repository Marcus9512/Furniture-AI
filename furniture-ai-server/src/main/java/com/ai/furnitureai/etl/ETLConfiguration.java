package com.ai.furnitureai.etl;

import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.Objects;

public class ETLConfiguration {

    private final DocumentTransformer transformer;
    private final VectorStore vectorStore;
    private final ETLManager etlManager;

    public ETLConfiguration(DocumentTransformer transformer, VectorStore vectorStore, ETLManager etlManager) {
        this.transformer = Objects.requireNonNull(transformer, "missing transformer");
        this.vectorStore = Objects.requireNonNull(vectorStore, "missing vectorstore");
        this.etlManager = Objects.requireNonNull(etlManager, "missing etlManager");
    }

    public DocumentTransformer getTransformer() {
        return transformer;
    }

    public VectorStore getVectorStore() {
        return vectorStore;
    }

    public ETLManager getEtlManager() {
        return etlManager;
    }
}
