package com.ai.furnitureai.tools;

import com.ai.furnitureai.utils.Order;
import com.ai.furnitureai.services.OrderDatabaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;


import java.util.UUID;

public class OrderTool {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderTool.class);

    private final OrderDatabaseService orderDatabaseService;

    public OrderTool(OrderDatabaseService orderDatabaseService) {
        this.orderDatabaseService = orderDatabaseService;
    }

    @Tool(description = "Schedule and order on a furniture, returns an order id")
    public String scheduleOrder(
            @ToolParam(description = "id of product") String productId,
            @ToolParam(description = "Name of product") String productName,
            @ToolParam(description = "Name of product") String deliveryAddress) {

        LOGGER.info("Schedule Order called");

        String orderId = UUID.randomUUID().toString();

        orderDatabaseService.placeOrder(
                Order.builder()
                        .orderId(orderId)
                        .productId(productId)
                        .productName(productName)
                        .deliveryAddress(deliveryAddress)
                        .build()
        );

        logOrders();
        return orderId;
    }

    @Tool(description = "Check if order id exists")
    public String doesOrderExist(@ToolParam(description = "id of order") String orderId) {
        LOGGER.info("Order exists called");
        return orderDatabaseService.containsOrder(Order.builder().orderId(orderId).build()) ? "Order exists" : "Order does not exist";
    }

    @Tool(description = "Get all orders")
    public String getAllOrders() {
        LOGGER.info("Get all orders called");
        String ret = orderDatabaseService.getAllOrder();
        LOGGER.info("returning {}", ret);
        return ret;
    }

    @Tool(description = "Remove order")
    public void removeOrder(@ToolParam(description = "id of order to remove") String orderId) {
        LOGGER.info("Remove order exists called");
        orderDatabaseService.removeOrder(Order.builder().orderId(orderId).build());
        logOrders();
    }

    private void logOrders() {
        LOGGER.info("----------PLACED ORDERS-------------");
        LOGGER.info(orderDatabaseService.getAllOrder());
        LOGGER.info("------------------------------------");
    }

}
