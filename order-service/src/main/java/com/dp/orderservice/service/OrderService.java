package com.dp.orderservice.service;

import com.dp.orderservice.dto.OrderLineItemsDTO;
import com.dp.orderservice.dto.OrderRequest;
import com.dp.orderservice.model.Order;
import com.dp.orderservice.model.OrderLineItems;
import com.dp.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList =orderRequest.getOrderLineItemsDTOList().stream().map(this::createOrderLineItem).toList();
        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);
    }

    private OrderLineItems createOrderLineItem(OrderLineItemsDTO orderLineItemDTO) {
        OrderLineItems lineItems = new OrderLineItems();
        lineItems.setId(orderLineItemDTO.getId());
        lineItems.setPrice(orderLineItemDTO.getPrice());
        lineItems.setQuantity(orderLineItemDTO.getQuantity());
        lineItems.setSkuCode(orderLineItemDTO.getSkuCode());
        return lineItems;
    }
}
