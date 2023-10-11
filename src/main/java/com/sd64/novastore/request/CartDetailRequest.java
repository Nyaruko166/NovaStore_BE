package com.sd64.novastore.request;

import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.ProductDetail;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Setter
@Getter
public class CartDetailRequest {
    @NotNull(message = "quantity khong duoc de trong")
    private String quantity;

    @NotNull(message = "price khong duoc de trong")
    private String price;

    @NotNull(message = "priceAfter khong duoc de trong")
    private String priceAfter;


    private String createDate;


    private String updateDate;


    private String status;

    private String cartId;

    private String productDetailId;

    public CartDetail map(CartDetail cartDetail) {
        cartDetail.setQuantity(Integer.valueOf(this.getQuantity()));
        cartDetail.setPrice(BigDecimal.valueOf(Long.parseLong(this.getPrice())));
        cartDetail.setPriceAfter(BigDecimal.valueOf(Long.parseLong(this.getPriceAfter())));
        cartDetail.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        cartDetail.setUpdateDate(Date.from(Instant.parse(this.getUpdateDate())));
        cartDetail.setStatus(Integer.valueOf(this.getStatus()));
        cartDetail.setCart(Cart.builder().id(Integer.valueOf(this.getCartId())).build());
        cartDetail.setProductDetail(ProductDetail.builder().id(Integer.valueOf(this.getProductDetailId())).build());
        return cartDetail;
    }
}
