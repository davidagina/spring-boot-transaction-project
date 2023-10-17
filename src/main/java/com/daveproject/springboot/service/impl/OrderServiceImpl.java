package com.daveproject.springboot.service.impl;

import com.daveproject.springboot.dto.OrderRequest;
import com.daveproject.springboot.dto.OrderResponse;
import com.daveproject.springboot.entity.Order;
import com.daveproject.springboot.entity.Payment;
import com.daveproject.springboot.exception.PaymentException;
import com.daveproject.springboot.repository.OrderRepository;
import com.daveproject.springboot.repository.PaymentRepository;
import com.daveproject.springboot.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {


    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository){
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional(rollbackFor = PaymentException.class)
    public OrderResponse  placeOrder(OrderRequest orderRequest) {

        Order order = orderRequest.getOrder();
        order.setStatus("INPROGRESS");
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
        orderRepository.save(order);

        Payment payment = orderRequest.getPayment();
        if(!payment.getType().equals("DEBIT")){
            throw new PaymentException("Payment card type is not supported");
        }

        payment.setOrderId(order.getId());
        paymentRepository.save(payment);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderTrackingNumber(order.getOrderTrackingNumber());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setMessage("SUCCESS");

        return orderResponse;
    }
}
