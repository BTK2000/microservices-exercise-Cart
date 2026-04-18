package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;

import java.util.List;

public interface CartService {

    Cart createCart(Cart cart);

    Cart getCartById(Integer id);

    List<Cart> getAllCarts();

    CartItem addCartItem(CartItem cartItem);
}