package com.daveproject.springboot.dto;

import com.daveproject.springboot.entity.Order;
import com.daveproject.springboot.entity.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private Order order;
    private Payment payment;
}
