package com.example.orderservice.controllers;

import com.example.orderservice.messagingrabbitmq.RabbitMQConfiguration;
import entities.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.SimpleTimeZone;

@RestController
public class OrderRestController {

    private final RabbitTemplate rabbitTemplate;

    public OrderRestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/orders")
    String orderPizza(@RequestBody Order orderBody) {
        var orderId = (int) (Math.random() * 10000);
        Date date = new Date();
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")).toString();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String dateStr = formatter.format(date);
        System.out.println(orderId);
        Order order = new Order(orderId, dateStr, time);

        System.out.println(order);
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.topicExchangeName,
                "foo.bar.baz", "One Pizza with: " + order);

        return "Thanks for your order, you will soon recieve update from our partner pizza restaurant. ";
    }
}
