package com.ai.furnitureai;

import com.ai.furnitureai.config.AIConfig;
import com.ai.furnitureai.config.RagConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class FurnitureAIApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(
                FurnitureAIApplication.class,
                RagConfig.class,
                AIConfig.class
        ).run(args);
    }

}
