package com.example.cartservice.repository;

import com.example.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query(value = "SELECT * FROM cart_items WHERE quantity > ?1", nativeQuery = true)
    List<CartItem> findCartItemsAboveQuantityNative(Integer quantity);
}