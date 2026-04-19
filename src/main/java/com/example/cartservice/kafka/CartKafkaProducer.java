package com.example.cartservice.kafka;

import com.example.cartservice.dto.CartEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CartKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(CartKafkaProducer.class);
    private static final String TOPIC_NAME = "cart-topic";

    private final KafkaTemplate<String, CartEvent> kafkaTemplate;

    public CartKafkaProducer(KafkaTemplate<String, CartEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCartEvent(CartEvent cartEvent) {
        kafkaTemplate.send(TOPIC_NAME, cartEvent);
        log.info("Kafka event published: cartId={}, productId={}, quantity={}",
                cartEvent.getCartId(), cartEvent.getProductId(), cartEvent.getQuantity());
    }
}