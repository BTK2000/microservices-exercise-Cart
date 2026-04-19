package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartService {

    Cart createCart(Cart cart);

    Cart getCartById(Integer id);

    List<Cart> getAllCarts();

    CartItem addCartItem(CartItem cartItem);

    CartItem addCartItemWithValidation(CartItem cartItem);

    Page<Cart> getCartsPaged(int page, int size, String sortBy);

    Page<CartItem> getCartItemsPaged(int page, int size, String sortBy);

    List<CartItem> getCartItemsAboveQuantity(Integer quantity);

    List<Integer> getAllProductIdsFromCartItems();

    List<CartItem> getCartItemsAboveQuantityNative(Integer quantity);


}