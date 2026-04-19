package com.example.cartservice.controller;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        return cartService.createCart(cart);
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Integer id) {
        return cartService.getCartById(id);
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @PostMapping("/items")
    public CartItem addCartItem(@RequestBody CartItem cartItem) {
        return cartService.addCartItem(cartItem);
    }

    @GetMapping("/paged")
    public Page<Cart> getCartsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        return cartService.getCartsPaged(page, size, sortBy);
    }

    @GetMapping("/items/paged")
    public Page<CartItem> getCartItemsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy
    ) {
        return cartService.getCartItemsPaged(page, size, sortBy);
    }

    @GetMapping("/items/filter/quantity")
    public List<CartItem> getCartItemsAboveQuantity(@RequestParam Integer quantity) {
        return cartService.getCartItemsAboveQuantity(quantity);
    }

    @GetMapping("/items/product-ids")
    public List<Integer> getAllProductIdsFromCartItems() {
        return cartService.getAllProductIdsFromCartItems();
    }

    @GetMapping("/items/native/quantity")
    public List<CartItem> getCartItemsAboveQuantityNative(@RequestParam Integer quantity) {
        return cartService.getCartItemsAboveQuantityNative(quantity);
    }
}