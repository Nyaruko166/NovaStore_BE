package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.model.SessionCartItem;
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
        BigDecimal unitPrice = productDetail.getPrice();
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
    public SessionCart addToCartSession(SessionCart sessionCart, ProductDetail productDetail, Integer quantity) {
        SessionCartItem cartItem = findInSession(sessionCart, productDetail.getId());
        if (sessionCart == null){
            sessionCart = new SessionCart();
        }
        Set<SessionCartItem> cartDetailList = sessionCart.getCartDetails();
        BigDecimal unitPrice = productDetail.getPrice();
        int itemQuantity = 0;
        if (cartDetailList == null){
            cartDetailList = new HashSet<>();
            if (cartItem == null){
                cartItem = new SessionCartItem();
                cartItem.setProductDetail(productDetail);
                cartItem.setCart(sessionCart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartDetailList.add(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        } else {
            if (cartItem == null){
                cartItem = new SessionCartItem();
                cartItem.setProductDetail(productDetail);
                cartItem.setCart(sessionCart);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(unitPrice);
                cartDetailList.add(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
            }
        }
        sessionCart.setCartDetails(cartDetailList);

        BigDecimal totalPrice = totalPriceSession(cartDetailList);
        int totalItems = totalItemSession(cartDetailList);

        sessionCart.setTotalPrice(totalPrice);
        sessionCart.setTotalItems(totalItems);

        return sessionCart;
    }

    @Override
    public SessionCart updateCartSession(SessionCart sessionCart, ProductDetail productDetail, Integer quantity) {
        Set<SessionCartItem> cardItemList = sessionCart.getCartDetails();
        SessionCartItem item = findInSession(sessionCart, productDetail.getId());
        int itemQuantity = quantity;
        int totalItems = totalItemSession(cardItemList);
        BigDecimal totalPrice = totalPriceSession(cardItemList);
        item.setQuantity(itemQuantity);
        sessionCart.setCartDetails(cardItemList);
        sessionCart.setTotalPrice(totalPrice);
        sessionCart.setTotalItems(totalItems);
        return sessionCart;
    }

    @Override
    public SessionCart removeFromCartSession(SessionCart sessionCart, ProductDetail productDetail) {
        Set<SessionCartItem> cardItemList = sessionCart.getCartDetails();
        SessionCartItem item = findInSession(sessionCart, productDetail.getId());
        cardItemList.remove(item);
        int totalItems = totalItemSession(cardItemList);
        BigDecimal totalPrice = totalPriceSession(cardItemList);
        sessionCart.setCartDetails(cardItemList);
        sessionCart.setTotalItems(totalItems);
        sessionCart.setTotalPrice(totalPrice);
        return sessionCart;
    }

    @Override
    @Transactional
    public Cart combineCart(SessionCart sessionCart, String email) {
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();
        if (cart == null){
            cart = new Cart();
        }
        Set<CartDetail> cartDetails = cart.getCartDetails();
        if (cartDetails == null){
            cartDetails = new HashSet<>();
        }
        Set<CartDetail> sessionCartDetails = convertCartItem(sessionCart.getCartDetails(), cart);
        for (CartDetail cartDetail : sessionCartDetails){
            cartDetails.add(cartDetail);
            itemRepository.save(cartDetail);
        }
        BigDecimal totalPrice = totalPrice(cartDetails);
        int totalItems = totalItem(cartDetails);
        cart.setTotalItems(totalItems);
        cart.setCartDetails(cartDetails);
        cart.setTotalPrice(totalPrice);
        cart.setCustomer(customer);
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

    private SessionCartItem findInSession(SessionCart sessionCart, Integer productDetailId) {
        if (sessionCart == null) {
            return null;
        }
        SessionCartItem cartItem = null;
        for (SessionCartItem item : sessionCart.getCartDetails()) {
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

    private int totalItemSession(Set<SessionCartItem> cartItemList) {
        int totalItem = 0;
        for (SessionCartItem item : cartItemList) {
            totalItem += item.getQuantity();
        }
        return totalItem;
    }

    private BigDecimal totalPriceSession(Set<SessionCartItem> cartItemList) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (SessionCartItem item : cartItemList) {
            BigDecimal price = item.getPrice();
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal subTotal = price.multiply(quantity);
            totalPrice = totalPrice.add(subTotal);
        }
        return totalPrice;

    }

    private Set<CartDetail> convertCartItem(Set<SessionCartItem> sessionCartItems, Cart cart){
        Set<CartDetail> cartDetails = new HashSet<>();
        for (SessionCartItem sessionCartItem : sessionCartItems){
            CartDetail cartDetail = find(cart.getCartDetails(), sessionCartItem.getProductDetail().getId());
            if (cartDetail != null){
                if (cartDetail.getQuantity() < sessionCartItem.getQuantity()){
                    cartDetail.setQuantity(sessionCartItem.getQuantity());
                    cartDetails.add(cartDetail);
                }
            } else {
                CartDetail cartItem = new CartDetail();
                cartItem.setQuantity(sessionCartItem.getQuantity());
                cartItem.setPrice(sessionCartItem.getPrice());
                cartItem.setProductDetail(sessionCartItem.getProductDetail());
                cartItem.setCart(cart);
                cartDetails.add(cartItem);
            }
        }
        return cartDetails;
    }

    @Override
    public Cart getCart(String email) {
        Customer customer = customerService.findByEmail(email);
        Cart cart = customer.getCart();
        return cart;
    }
}
