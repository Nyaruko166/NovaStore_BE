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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashSet;
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

    @Override
    @Transactional
    public Cart updateCart(ProductDetail productDetail, Integer quantity, String email) {
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();
        Set<CartDetail> cardItemList = cart.getCartDetails();
        CartDetail item = find(cardItemList, productDetail.getId());
        int itemQuantity = quantity;

        item.setQuantity(itemQuantity);
        itemRepository.save(item);
        cart.setCartDetails(cardItemList);
        int totalItems = totalItem(cardItemList);
        BigDecimal totalPrice = totalPrice(cardItemList);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Cart removeFromCart(ProductDetail productDetail, String email) {
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();
        Set<CartDetail> cardItemList = cart.getCartDetails();
        CartDetail item = find(cardItemList, productDetail.getId());
        cardItemList.remove(item);
        itemRepository.delete(item);
        int totalItems = totalItem(cardItemList);
        BigDecimal totalPrice = totalPrice(cardItemList);
        cart.setCartDetails(cardItemList);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartById(Integer id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        if(!ObjectUtils.isEmpty(cart) && !ObjectUtils.isEmpty(cart.getCartDetails())){
            itemRepository.deleteAll(cart.getCartDetails());
        }
        cart.getCartDetails().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cart.setTotalItems(0);
        cartRepository.save(cart);
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
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();
        return cart;
    }
}
