package com.daveproject.springboot.service;

import com.daveproject.springboot.dto.OrderRequest;
import com.daveproject.springboot.dto.OrderResponse;
import org.springframework.stereotype.Service;


public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);
}
