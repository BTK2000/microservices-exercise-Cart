package com.example.cartservice.kafka;

import com.example.cartservice.dto.CartEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartKafkaProducer {

    private static final String TOPIC_NAME = "cart-topic";

    private final KafkaTemplate<String, CartEvent> kafkaTemplate;

    public CartKafkaProducer(KafkaTemplate<String, CartEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartEvent(CartEvent cartEvent) {
        kafkaTemplate.send(TOPIC_NAME, cartEvent);
        System.out.println("Cart event published to Kafka: " + cartEvent.getCartId()
                + ", " + cartEvent.getProductId()
                + ", " + cartEvent.getQuantity());
    }
}
