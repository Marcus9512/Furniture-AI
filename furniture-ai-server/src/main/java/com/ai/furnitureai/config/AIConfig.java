package com.ai.furnitureai.config;

import com.ai.furnitureai.prompts.CustomPrompt;
import com.ai.furnitureai.prompts.SimpleAgentPrompt;
import com.ai.furnitureai.tools.OrderTool;
import com.ai.furnitureai.services.OrderDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AIConfig {

    @Bean
    public CustomPrompt customPrompt() {
        return new SimpleAgentPrompt();
    }

    @Bean
    public OrderDatabaseService orderDatabase() {
        return new OrderDatabaseService();
    }

    @Bean
    public OrderTool orderTool(OrderDatabaseService orderDatabaseService) {
        return new OrderTool(orderDatabaseService);
    }
}
