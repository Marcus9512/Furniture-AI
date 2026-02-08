package com.ai.furnitureai.services;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalDBService {

    private final VectorStore vectorStore;

    public ExternalDBService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }


    public List<Document> doSimilaritySearch(String query,
                                             double similarityThreshold,
                                             int topK) {

        return vectorStore.similaritySearch(SearchRequest.builder()
                .query(query)
                .similarityThreshold(similarityThreshold)
                .topK(topK)
                .build()
        );
    }
}
