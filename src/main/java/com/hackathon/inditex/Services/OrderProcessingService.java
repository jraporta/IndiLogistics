package com.hackathon.inditex.Services;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Entities.OrderProcessingDetails;
import com.hackathon.inditex.Entities.OrderProcessingDetailsWithMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderProcessingService {

    private CenterService centerService;
    private OrderService orderService;

    public List<OrderProcessingDetails> processOrders() {
        List<OrderProcessingDetails> processedOrdersDetails = new ArrayList<>();
        orderService.getAllOrders().stream()
                .filter(order -> order.getStatus().equals("PENDING"))
                .sorted((o1, o2) -> Long.compare(o1.getId(), o2.getId()))
                .forEach(order -> {
                    processedOrdersDetails.add(processOrder(order));
                });
        return processedOrdersDetails;
    }

    private OrderProcessingDetails processOrder(Order order) {
        List<Center> availableCenters = centerService.getAllCenters().stream()
                .filter(center -> center.getStatus().equalsIgnoreCase("AVAILABLE"))
                .toList();

        if (availableCenters.isEmpty()) {
            return processUnassignedOrder(order, "There are no available centers.");
        }

        availableCenters = availableCenters.stream()
                .filter(center -> center.getCapacity().toUpperCase().contains(order.getSize().toUpperCase()))
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
