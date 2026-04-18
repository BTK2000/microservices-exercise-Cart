package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;

public interface CartService {

    Cart createCart(Cart cart);

    Cart getCartById(Integer id);

    CartItem addCartItem(CartItem cartItem);
}