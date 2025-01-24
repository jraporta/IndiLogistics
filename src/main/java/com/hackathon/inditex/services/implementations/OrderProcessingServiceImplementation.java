package com.hackathon.inditex.services.implementations;

import com.hackathon.inditex.entities.Center;
import com.hackathon.inditex.entities.Order;
import com.hackathon.inditex.entities.OrderProcessingDetails;
import com.hackathon.inditex.entities.OrderProcessingDetailsWithMessage;
import com.hackathon.inditex.services.CenterService;
import com.hackathon.inditex.services.OrderProcessingService;
import com.hackathon.inditex.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderProcessingServiceImplementation implements OrderProcessingService {

    private CenterService centerService;
    private OrderService orderService;

    @Override
    public List<OrderProcessingDetails> processOrders() {
        List<OrderProcessingDetails> processedOrdersDetails = new ArrayList<>();
        orderService.getAllOrders().stream()
                .filter(order -> "PENDING".equalsIgnoreCase(order.getStatus()))
                .sorted(Comparator.comparingLong(Order::getId))
                .forEach(order -> processedOrdersDetails.add(processOrder(order)));
        return processedOrdersDetails;
    }

    private OrderProcessingDetails processOrder(Order order) {
        List<Center> availableCenters = centerService.getAllCenters().stream()
                .filter(center -> "AVAILABLE".equalsIgnoreCase(center.getStatus()))
                .toList();

        if (availableCenters.isEmpty()) {
            return processUnassignedOrder(order, "There are no available centers.");
        }

        availableCenters = availableCenters.stream()
                .filter(center -> center.getCapacity().toUpperCase(Locale.US)
                        .contains(order.getSize().toUpperCase(Locale.US)))
                .toList();

        if (availableCenters.isEmpty()) {
            return processUnassignedOrder(order, "No available centers support the order type.");
        }

        availableCenters = availableCenters.stream()
                .filter(center -> center.getCurrentLoad() < center.getMaxCapacity())
                .toList();

        if (availableCenters.isEmpty()) {
            return processUnassignedOrder(order, "All centers are at maximum capacity.");
        }

        Center closestCenter = availableCenters.stream()
                .min(Comparator.comparingDouble(c ->
                        HaversineDistance.calculateDistance(c.getCoordinates(), order.getCoordinates())))
                .get();

        return processAssignedOrder(closestCenter, order);

    }

    private OrderProcessingDetails processUnassignedOrder(Order order, String message) {
        OrderProcessingDetailsWithMessage details = new OrderProcessingDetailsWithMessage();
        details.setDistance(null);
        details.setOrderId(order.getId());
        details.setAssignedLogisticsCenter(null);
        details.setMessage(message);
        details.setStatus(order.getStatus());
        return details;
    }


    private OrderProcessingDetails processAssignedOrder(Center center, Order order) {
        centerService.increaseCurrentLoad(center);
        orderService.assignCenter(order, center.getName());
        return createDetails(order, center);
    }

    private OrderProcessingDetails createDetails(Order order, Center center) {
        OrderProcessingDetails details = new OrderProcessingDetails();
        details.setDistance(HaversineDistance.calculateDistance(center.getCoordinates(), order.getCoordinates()));
        details.setOrderId(order.getId());
        details.setAssignedLogisticsCenter(center.getName());
        details.setStatus(order.getStatus());
        return details;
    }


}
