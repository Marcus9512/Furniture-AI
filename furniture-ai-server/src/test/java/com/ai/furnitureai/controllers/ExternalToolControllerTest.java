package com.ai.furnitureai.controllers;

import com.ai.furnitureai.services.OrderDatabaseService;
import com.ai.furnitureai.tools.OrderTool;
import com.ai.furnitureai.utils.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ExternalToolControllerTest {

    private OrderDatabaseService orderDatabaseService;
    private ExternalToolController externalToolController;

    @BeforeEach
    public void setup() {
        this.orderDatabaseService = new OrderDatabaseService();
        this.externalToolController = new ExternalToolController(new OrderTool(orderDatabaseService));
    }

    @Test
    public void scheduleOrder() {
        String orderId = externalToolController.scheduleOrder("pid1", "p1", "d1").block();

        Order order = Order.builder()
                .orderId(orderId)
                .productName("p1")
                .deliveryAddress("d1")
                .build();
        assertThat(orderDatabaseService.containsOrder(order)).isTrue();
    }

    @Test
    public void doesOrderExist() {
        String orderId = externalToolController.scheduleOrder("pid1", "p1", "d1").block();

        assertThat(externalToolController.doesOrderExist(orderId).block()).isEqualTo("Order exists");
        assertThat(externalToolController.doesOrderExist("o2").block()).isEqualTo("Order does not exist");
    }

    @Test
    public void removeOrder() {
        String orderId = externalToolController.scheduleOrder("pid1", "p1", "d1").block();
        String orderId2 = externalToolController.scheduleOrder("pid1", "p1", "d1").block();

        assertThat(externalToolController.doesOrderExist(orderId).block()).isEqualTo("Order exists");
        assertThat(externalToolController.doesOrderExist(orderId2).block()).isEqualTo("Order exists");

        externalToolController.removeOrder(orderId2).block();

        assertThat(externalToolController.doesOrderExist(orderId).block()).isEqualTo("Order exists");
        assertThat(externalToolController.doesOrderExist(orderId2).block()).isEqualTo("Order does not exist");
    }

    @Test
    public void getAllOrdersTest() {
        String orderId = externalToolController.scheduleOrder("pid1", "p1", "d1").block();
        String orderId2 = externalToolController.scheduleOrder("pid1", "p1", "d1").block();

        String res = externalToolController.getAllOrders().block();

        assertThat(res).isEqualTo("Order{orderId='" + orderId +"', productId='pid1', productName='p1', deliveryAddress='d1'}\n" +
                "Order{orderId='" + orderId2 + "', productId='pid1', productName='p1', deliveryAddress='d1'}\n");
    }


}