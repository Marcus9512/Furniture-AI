package com.ai.furnitureai.services;

import com.ai.furnitureai.advisors.SimpleLoggerAdvisor;
import com.ai.furnitureai.prompts.CustomPrompt;
import com.ai.furnitureai.tools.OrderTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private ChatClient chatClient;
    private OrderTool orderTool;

    public AgentService(ChatClient.Builder chatBuilder,
                        ChatMemory chatMemory,
                        VectorStore vectorStore,
                        CustomPrompt customPrompt,
                        OrderTool orderTool
    ) {
        this.orderTool = orderTool;
        this.chatClient = chatBuilder.defaultAdvisors()
                .defaultSystem(customPrompt.getSystemMessage())
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        QuestionAnswerAdvisor.builder(vectorStore).build(),
                        new SimpleLoggerAdvisor()
                )
                .build();


    }

    public String chat(String question) {
        return chatClient.prompt()
                .tools(orderTool)
                .user(question)
                .call()
                .content();
    }
}
