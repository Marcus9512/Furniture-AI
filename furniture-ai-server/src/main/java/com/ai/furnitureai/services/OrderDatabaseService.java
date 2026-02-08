package com.ai.furnitureai.services;

import com.ai.furnitureai.utils.Order;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderDatabaseService {

    private Set<Order> placedOrder = new HashSet<>();

    public synchronized void placeOrder(Order order) {
        placedOrder.add(order);
    }

    public synchronized void removeOrder(Order order) {
        placedOrder.remove(order);
    }

    public synchronized boolean containsOrder(Order order) {
        return placedOrder.contains(order);
    }

    public synchronized String getAllOrder() {
        StringBuilder stringBuilder = new StringBuilder();
        placedOrder.forEach(o -> stringBuilder.append(o.toString())
                .append("\n"));
        return stringBuilder.toString();
    }
}
