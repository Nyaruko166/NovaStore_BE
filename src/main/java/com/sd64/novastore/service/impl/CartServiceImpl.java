package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.CartDetailRepository;
import com.sd64.novastore.repository.CartRepository;
import com.sd64.novastore.service.CartService;
import com.sd64.novastore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartDetailRepository itemRepository;

    @Autowired
    private CustomerService customerService;

    @Override
    @Transactional
    public Cart addToCart(ProductDetail productDetail, Integer quantity, String email) {
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();

        if (cart == null){
            cart = new Cart();
        }
        Set<CartDetail> cartDetailList = cart.getCartDetails();
        CartDetail cartItem = find(cartDetailList, productDetail.getId());
        BigDecimal unitPrice = productDetail.getProduct().getPrice();
        int itemQuantity = 0;
        if (cartDetailList == null){
            cartDetailList = new HashSet<>();
            if (cartItem == null){
                cartItem = new CartDetail();
                cartItem.setProductDetail(productDetail);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartItem.setCart(cart);
                cartDetailList.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null){
                cartItem = new CartDetail();
                cartItem.setProductDetail(productDetail);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartItem.setCart(cart);
                cartDetailList.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                itemRepository.save(cartItem);
            }
        }
        cart.setCartDetails(cartDetailList);

        BigDecimal totalPrice = totalPrice(cart.getCartDetails());
        int totalItems = totalItem(cart.getCartDetails());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setCustomer(customer);

        return cartRepository.save(cart);
    }

    private CartDetail find(Set<CartDetail> cartItems, Integer productDetailId) {
        if (cartItems == null) {
            return null;
        }
        CartDetail cartItem = null;
        for (CartDetail item : cartItems) {
            if (item.getProductDetail().getId() == productDetailId) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItem(Set<CartDetail> cartItemList) {
        int totalItem = 0;
        for (CartDetail item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private BigDecimal totalPrice(Set<CartDetail> cartItemList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartDetail item : cartItemList) {
            BigDecimal price = item.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal subTotal = price.multiply(quantity);
            totalPrice = totalPrice.add(subTotal);
        }
        return totalPrice;

    }

    @Override
    public Cart getCart(String email) {
        return null;
    }
}
