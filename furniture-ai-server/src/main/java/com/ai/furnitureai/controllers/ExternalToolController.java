package com.ai.furnitureai.controllers;

import com.ai.furnitureai.tools.OrderTool;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/tools")
public class ExternalToolController {

    private OrderTool orderTool;

    public ExternalToolController(OrderTool orderTool) {
        this.orderTool = orderTool;
    }

    @Operation(summary = "Schedule an order", description = "Schedule an order and place it in order database")
    @GetMapping(value = "/orders/schedule")
    public Mono<String> scheduleOrder(
            @RequestParam String productId,
            @RequestParam String productName,
            @RequestParam String deliveryAddress
    ) {
        return Mono.just(orderTool.scheduleOrder(productId, productName, deliveryAddress));
    }

    @Operation(summary = "Check if orderId exists in database")
    @GetMapping(value = "/orders/exists")
    public Mono<String> doesOrderExist(
            @RequestParam String orderId
    ) {
        return Mono.just(orderTool.doesOrderExist(orderId));
    }

    @Operation(summary = "Remove order from database")
    @GetMapping(value = "/orders/remove")
    public Mono<Void> removeOrder(
            @RequestParam String orderId
    ) {
        return Mono.fromRunnable(() -> orderTool.removeOrder(orderId));
    }

    @Operation(summary = "Returns all orders")
    @GetMapping(value = "/orders/get-all")
    public Mono<String> getAllOrders() {
        return Mono.just(orderTool.getAllOrders());
    }
}
