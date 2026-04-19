package com.example.cartservice.controller;

import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.service.CartService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/carts")
public class CartController {

    private static final Logger log = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public Cart createCart(@RequestBody Cart cart) {
        log.info("API call: create cart for userId={}", cart.getUserId());
        return cartService.createCart(cart);
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable Integer id) {
        log.info("API call: get cart by id={}", id);
        return cartService.getCartById(id);
    }

    @GetMapping
    public List<Cart> getAllCarts() {
        log.info("API call: get all carts");
        return cartService.getAllCarts();
    }

    @PostMapping("/items")
    public CartItem addCartItem(@Valid @RequestBody CartItem cartItem) {
        log.info("API call: add cart item cartId={}, productId={}, quantity={}",
                cartItem.getCartId(), cartItem.getProductId(), cartItem.getQuantity());
        return cartService.addCartItem(cartItem);
    }

    @PostMapping("/items/validate")
    public CartItem addCartItemWithValidation(@Valid @RequestBody CartItem cartItem) {
        log.info("API call: add validated cart item cartId={}, productId={}, quantity={}",
                cartItem.getCartId(), cartItem.getProductId(), cartItem.getQuantity());
        return cartService.addCartItemWithValidation(cartItem);
    }

    @PostMapping("/items/validate/async")
    public CompletableFuture<CartItem> addCartItemAsyncWithValidation(@Valid @RequestBody CartItem cartItem) {
        log.info("API call: async validated cart item cartId={}, productId={}, quantity={}",
                cartItem.getCartId(), cartItem.getProductId(), cartItem.getQuantity());
        return cartService.addCartItemAsyncWithValidation(cartItem);
    }

    @GetMapping("/paged")
    public Page<Cart> getCartsPaged(@RequestParam int page,
                                    @RequestParam int size,
                                    @RequestParam String sortBy) {
        log.info("API call: get paged carts page={}, size={}, sortBy={}", page, size, sortBy);
        return cartService.getCartsPaged(page, size, sortBy);
    }

    @GetMapping("/items/paged")
    public Page<CartItem> getCartItemsPaged(@RequestParam int page,
                                            @RequestParam int size,
                                            @RequestParam String sortBy) {
        log.info("API call: get paged cart items page={}, size={}, sortBy={}", page, size, sortBy);
        return cartService.getCartItemsPaged(page, size, sortBy);
    }

    @GetMapping("/items/filter/quantity")
    public List<CartItem> getCartItemsAboveQuantity(@RequestParam Integer quantity) {
        log.info("API call: filter cart items above quantity={}", quantity);
        return cartService.getCartItemsAboveQuantity(quantity);
    }

    @GetMapping("/items/product-ids")
    public List<Integer> getAllProductIdsFromCartItems() {
        log.info("API call: get all product ids from cart items");
        return cartService.getAllProductIdsFromCartItems();
    }

    @GetMapping("/items/native/quantity")
    public List<CartItem> getCartItemsAboveQuantityNative(@RequestParam Integer quantity) {
        log.info("API call: native query cart items above quantity={}", quantity);
        return cartService.getCartItemsAboveQuantityNative(quantity);
    }
}