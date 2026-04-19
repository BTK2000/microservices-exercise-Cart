package com.example.cartservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer cartId;

    @Column(nullable = false)
    private Integer productId;

    @NotNull(message = "Quantity should not be null")
    @Positive(message = "Quantity must be greater than 0")
    @Column(nullable = false)
    private Integer quantity;
}