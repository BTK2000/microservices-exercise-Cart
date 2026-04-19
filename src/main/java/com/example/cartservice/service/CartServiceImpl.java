package com.example.cartservice.service;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(Integer id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found with id: " + id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public CartItem addCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Page<Cart> getCartsPaged(int page, int size, String sortBy) {
        return cartRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @Override
    public Page<CartItem> getCartItemsPaged(int page, int size, String sortBy) {
        return cartItemRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    @Override
    public List<CartItem> getCartItemsAboveQuantity(Integer quantity) {
        return cartItemRepository.findAll()
                .stream()
                .filter(item -> item.getQuantity() > quantity)
                .toList();
    }

    @Override
    public List<Integer> getAllProductIdsFromCartItems() {
        return cartItemRepository.findAll()
                .stream()
                .map(CartItem::getProductId)
                .toList();
    }

    @Override
    public List<CartItem> getCartItemsAboveQuantityNative(Integer quantity) {
        return cartItemRepository.findCartItemsAboveQuantityNative(quantity);
    }
}