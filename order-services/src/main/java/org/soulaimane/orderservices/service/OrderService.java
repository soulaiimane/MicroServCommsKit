package org.soulaimane.orderservices.service;

import lombok.AllArgsConstructor;
import org.soulaimane.orderservices.dto.InventoryResponse;
import org.soulaimane.orderservices.dto.OrderLineItemsRequest;
import org.soulaimane.orderservices.dto.OrderRequest;
import org.soulaimane.orderservices.entities.Order;
import org.soulaimane.orderservices.entities.OrderLineItems;
import org.soulaimane.orderservices.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String saveOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsRequests()
                .stream()
                .map(o -> mapToOrderLineItems(o)).toList();

        order.setOrderLineItems(orderLineItems);


        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode).toList();


        //Call Inventory Service and check if the product or the item is in stock or not
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean AllProductIsInStock = Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if (AllProductIsInStock){
            orderRepository.save(order);
            
            return "Order Added Successfully";
        }else {
            throw new IllegalArgumentException(" Product Not Found , Please Try Again Later");
        }


    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsRequest orderLineItemsRequest) {
        OrderLineItems orderLineItems=new OrderLineItems(null,orderLineItemsRequest.getSkuCode(),orderLineItemsRequest.getPrice()
                ,orderLineItemsRequest.getQuantity());
        return orderLineItems;
    }
}
