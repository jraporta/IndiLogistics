package com.hackathon.inditex.services.implementations;

import com.hackathon.inditex.entities.Center;
import com.hackathon.inditex.entities.Order;
import com.hackathon.inditex.entities.OrderProcessingDetails;
import com.hackathon.inditex.entities.OrderProcessingDetailsWithMessage;
import com.hackathon.inditex.services.CenterService;
import com.hackathon.inditex.services.OrderProcessingService;
import com.hackathon.inditex.services.OrderService;
import com.hackathon.inditex.utils.DistanceCalculator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class OrderProcessingServiceImplementation implements OrderProcessingService {

    private CenterService centerService;
    private OrderService orderService;
    private DistanceCalculator distanceCalculator;

    @Override
    public List<OrderProcessingDetails> processOrders() {

        List<Center> availableCenters = getAvailableCenters();

        return orderService.getAllOrders().stream()
                .filter(this::isPendingOrder)
                .sorted(Comparator.comparingLong(Order::getId))
                .map(order -> processSingleOrder(order, availableCenters))
                .toList();
    }

    private boolean isPendingOrder(Order order) {
        return "PENDING".equalsIgnoreCase(order.getStatus());
    }

    private OrderProcessingDetails processSingleOrder(Order order, List<Center> availableCenters) {

        if (availableCenters.isEmpty()) {
            return processUnassignedOrder(order, "There are no available centers.");
        }

        List<Center> filteredCenters = filterByOrderType(order, availableCenters);

        if (filteredCenters.isEmpty()) {
            return processUnassignedOrder(order, "No available centers support the order type.");
        }

        filteredCenters = filterByCapacity(filteredCenters);

        if (filteredCenters.isEmpty()) {
            return processUnassignedOrder(order, "All centers are at maximum capacity.");
        }

        Center closestCenter = getClosestCenter(order, filteredCenters);

        return processAssignedOrder(closestCenter, order);

    }

    private Center getClosestCenter(Order order, List<Center> centers) {
        return centers.stream()
                .min(Comparator.comparingDouble(c ->
                        distanceCalculator.calculateDistance(c.getCoordinates(), order.getCoordinates())))
                .get();
    }

    private List<Center> filterByCapacity(List<Center> availableCenters) {
        return availableCenters.stream()
                .filter(center -> center.getCurrentLoad() < center.getMaxCapacity())
                .toList();
    }

    private List<Center> filterByOrderType(Order order, List<Center> availableCenters) {
        return availableCenters.stream()
                .filter(center -> center.getCapacity().toUpperCase(Locale.US)
                        .contains(order.getSize().toUpperCase(Locale.US)))
                .toList();
    }

    private List<Center> getAvailableCenters() {
        return centerService.getAllCenters().stream()
                .filter(center -> "AVAILABLE".equalsIgnoreCase(center.getStatus()))
                .toList();
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
        details.setDistance(distanceCalculator.calculateDistance(center.getCoordinates(), order.getCoordinates()));
        details.setOrderId(order.getId());
        details.setAssignedLogisticsCenter(center.getName());
        details.setStatus(order.getStatus());
        return details;
    }


}
