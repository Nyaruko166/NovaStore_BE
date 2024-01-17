/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.*;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.OfflineCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OfflineCartServiceImpl implements OfflineCartService {

    @Autowired
    private OfflineCartRepository repository;

    @Autowired
    private BillService billService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    //    List<OfflineCart> testCart = new ArrayList<>();

    @Override
    public List<OfflineCart> addToCart(Integer billId, String detailProductId, Integer qty) {
        List<OfflineCart> cart = getBillById(billId).getLstDetailProduct();
        for (OfflineCart x : cart) {
            if (x.getDetailProductId().equals(detailProductId)) {
                Integer slMoi = x.getQty() + qty;
                if (checkQty(detailProductId, slMoi)) {
                    x.setQty(slMoi);
                    return cart;
                }
                return null;
//                System.out.println("update cart");
            }
        }
        cart.add(new OfflineCart(detailProductId, qty));
//        System.out.println("thêm mới vào cart");
        return cart;
    }

    @Override
    public List<OfflineCartView> getCart(List<OfflineCart> cart) {
        List<OfflineCartView> lstCart = new ArrayList<>();
        List<ProductDetail> lstCtsp = productDetailRepository.findAll();
        for (OfflineCart x : cart) {
            for (ProductDetail y : lstCtsp) {
                if (y.getCode().equals(x.getDetailProductId())) {
                    lstCart.add(OfflineCartView.builder()
                            .name(y.getProduct().getName())
                            .idSP(y.getProduct().getId())
                            .idCtsp(y.getId())
                            .codeCtsp(y.getCode())
                            .qty(x.getQty())
                            .price(y.getPrice())
                            .color(y.getColor().getName())
                            .size(y.getSize().getName())
                            .build());
                }
            }
        }
        return lstCart;
    }

    @Override
    public String deleteCart(String codeCtsp, Integer billId) {
        List<OfflineCart> cart = getBillById(billId).getLstDetailProduct();
        for (OfflineCart x : cart) {
            if (x.getDetailProductId().equals(codeCtsp)) {
                cart.remove(x);
                return "Xoá Thành Công!";
            }
        }
        return "Xoá Thất Bại!";
    }

    @Override
    public void emptyCart(List<OfflineCart> empty) {
        repository.setCartSP(empty);
    }

    @Override
    public List<TempBill> getLstBill() {
        return repository.getLstTempBill();
    }

    @Override
    public Boolean addToLstBill(TempBill tempBill) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (Objects.equals(x.getBillId(), tempBill.getBillId())) {
                int index = lstBill.indexOf(x);
                lstBill.set(index, tempBill);
//                System.out.println("đã có, update");
                return true;
            }
        }
        lstBill.add(tempBill);
//        System.out.println("thêm mới");
        return true;
    }

    @Override
    public Boolean removeFromLstBill(TempBill tempBill) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (x.getBillId().equals(tempBill.getBillId()) && x.getBillId() == 0) {
                int index = lstBill.indexOf(x);
                lstBill.set(index, TempBill.builder().billId(0).billCode(genBillCode())
                        .lstDetailProduct(new ArrayList<>()).build());
//                System.out.println("xoa hoa don 0");
                return true;
            } else if (x.getBillId().equals(tempBill.getBillId())) {
                lstBill.remove(x);
//                System.out.println("xoa hoa don khac 0");
                return true;
            }
        }
        return false;
    }

    @Override
    public TempBill getBillById(Integer id) {
        List<TempBill> lstBill = repository.getLstTempBill();
        for (TempBill x : lstBill) {
            if (Objects.equals(x.getBillId(), id)) {
                return x;
            }
        }
        return null;
    }

    @Override
    public BigDecimal calCartPrice(List<OfflineCartView> lstCart) {
        BigDecimal total = BigDecimal.valueOf(0);
        for (OfflineCartView x : lstCart) {
            total = total.add(x.calTotalPrice());
        }
        return total;
    }

    @Override
    public TempBill checkout(TempBill tempBill) {

        Bill bill = billService.addBillPos(convertToBill(tempBill));
        List<OfflineCartView> lstItems = getCart(tempBill.getLstDetailProduct());
        for (OfflineCartView x : lstItems) {
            billService.addBillDetailPos(convertToBillDetail(x, bill));
            ProductDetail productDetail = productDetailRepository.findById(x.getIdCtsp()).orElse(null);
            productDetail.setQuantity(productDetail.getQuantity() - x.getQty());
            productDetailRepository.save(productDetail);
        }
        paymentMethodRepository.save(new PaymentMethod(null, "Bán tại quầy", bill.getTotalPrice(), "Bán tại quầy", 1, bill));
        removeFromLstBill(tempBill);
        return tempBill;
    }

    @Override
    public String genBillCode() {
        Random random = new Random();

        StringBuilder randomString = new StringBuilder("HD");
        for (int i = 0; i < 6; i++) {
            int randomNumber = random.nextInt(10);
            randomString.append(randomNumber);
        }

        return randomString.toString();
    }

    private Bill convertToBill(TempBill tempBill) {
        Date date = new Date();
        Customer customer = null;
        if (tempBill.getIdCustomer() != null) {
            customer = customerRepository.findById(tempBill.getIdCustomer()).orElse(null);
        } else {
            tempBill.setCustomerName("Khách Lẻ");
        }
        Account employee = null;
        if (tempBill.getIdEmployee() != null) {
            employee = accountRepository.findById(tempBill.getIdEmployee()).orElse(null);
        }
        return Bill.builder()
                .id(null)
                .code(tempBill.getBillCode())
                .type(1)
                .customerName(tempBill.getCustomerName())
                .address("Mua tại quầy")
                .phoneNumber(tempBill.getCustomerPhone())
                .status(1)
                .note(null)
                .orderDate(date)
                .paymentDate(date)
                .confirmationDate(date)
                .shippingDate(date)
                .completionDate(date)
                .cancellationDate(date)
                .shippingFee(BigDecimal.valueOf(0))
                .price(tempBill.getTotalCartPrice())
                .discountAmount(BigDecimal.valueOf(0))
                .totalPrice(tempBill.getTotalCartPrice())
                .createDate(date)
                .updateDate(date)
                .customer(customer)
                .employee(employee)
                .voucher(null)
                .build();
    }

    private BillDetail convertToBillDetail(OfflineCartView offlineCartView, Bill bill) {

        ProductDetail productDetail = productDetailRepository.findById(offlineCartView.getIdCtsp()).orElse(null);

        return BillDetail.builder()
                .id(null)
                .price(offlineCartView.getPrice())
                .quantity(offlineCartView.getQty())
                .bill(bill)
                .productDetail(productDetail)
                .build();
    }

    @Override
    public List<OfflineCart> updateCart(Integer billId, String codeCTSP, Integer qty) {
        List<OfflineCart> cart = getBillById(billId).getLstDetailProduct();
        if (checkQty(codeCTSP, qty)) {
            for (OfflineCart x : cart) {
                if (x.getDetailProductId().equals(codeCTSP)) {
                    x.setQty(qty);
//                    System.out.println("update qty cart");
                    return cart;
                }
            }
        }
        return null;
        //        System.out.println("thêm mới vào cart");
    }

    private Boolean checkQty(String codeCTSP, Integer qty) {

        List<ProductDetail> lstProd = productDetailRepository.findAll();

        for (ProductDetail y : lstProd) {
            if (codeCTSP.equals(y.getCode())) {
                if (qty > y.getQuantity()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean checkSlTon(List<OfflineCart> lstCart) {

        List<ProductDetail> lstProd = productDetailRepository.findAll();

        for (OfflineCart x : lstCart) {
            for (ProductDetail y : lstProd) {
                if (x.getDetailProductId().equals(y.getCode())) {
                    if (y.getQuantity() <= 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
