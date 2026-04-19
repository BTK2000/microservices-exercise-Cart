package com.example.cartservice.service;

import com.example.cartservice.dto.CartEvent;
import com.example.cartservice.dto.ProductResponse;
import com.example.cartservice.entity.Cart;
import com.example.cartservice.entity.CartItem;
import com.example.cartservice.kafka.CartKafkaProducer;
import com.example.cartservice.repository.CartItemRepository;
import com.example.cartservice.repository.CartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final WebClient webClient;
    private final CartKafkaProducer cartKafkaProducer;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           WebClient webClient,
                           CartKafkaProducer cartKafkaProducer) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.webClient = webClient;
        this.cartKafkaProducer = cartKafkaProducer;
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
    public CartItem addCartItemWithValidation(CartItem cartItem) {
        ProductResponse product = webClient.get()
                .uri("/products/{id}", cartItem.getProductId())
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        if (product == null) {
            throw new RuntimeException("Product not found with id: " + cartItem.getProductId());
        }

        if (product.getStock() < cartItem.getQuantity()) {
            throw new RuntimeException("Insufficient stock for product id: " + cartItem.getProductId());
        }

        CartItem savedItem = cartItemRepository.save(cartItem);

        CartEvent cartEvent = new CartEvent(
                savedItem.getCartId(),
                savedItem.getProductId(),
                savedItem.getQuantity()
        );

        cartKafkaProducer.sendCartEvent(cartEvent);

        return savedItem;
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