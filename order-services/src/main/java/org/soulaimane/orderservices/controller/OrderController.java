package org.soulaimane.orderservices.controller;

import brave.Tracer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import org.soulaimane.orderservices.dto.OrderRequest;
import org.soulaimane.orderservices.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
//@RequestMapping("/api/order")
public class OrderController {
    OrderService orderService;
    @PostMapping("/api/order")
    @CircuitBreaker(name = "inventory" ,fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture <String> saveOrder(@RequestBody  OrderRequest orderRequest){

        return CompletableFuture.supplyAsync(()-> orderService.saveOrder(orderRequest));
    }
    public CompletableFuture <String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops Something went wrong, Please try after some time! ");

    }
}
