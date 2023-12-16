package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.Customer;
import com.sd64.novastore.model.PaymentMethod;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.model.SessionCart;
import com.sd64.novastore.model.SessionCartItem;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.repository.AccountRepository;
import com.sd64.novastore.repository.AddressRepository;
import com.sd64.novastore.repository.BillDetailRepository;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.repository.BillRepository;
import com.sd64.novastore.repository.CustomerRepository;
import com.sd64.novastore.repository.PaymentMethodRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.RoleRepository;
import com.sd64.novastore.repository.VoucherRepository;
import com.sd64.novastore.service.BillService;
import com.sd64.novastore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillDetailRepository billDetailRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAllByStatus(1);
    }

    @Override
    public Page<Bill> getAllBillPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OrderDate"));
        return billRepository.findAll(pageable);
    }

    @Override
    public Bill addBill(Bill bill) {
        bill.setStatus(1);
        bill.setCreateDate(new Date());
        bill.setUpdateDate(new Date());
        return billRepository.save(bill);
    }

    @Override
    public Bill updateBill(Bill bill, Integer id) {
        Optional<Bill> optional = billRepository.findById(id);
        if (optional.isPresent()) {
            Bill oldBill = optional.get();
            bill.setId(oldBill.getId());
            bill.setStatus(oldBill.getStatus());
            bill.setCreateDate(oldBill.getCreateDate());
            bill.setUpdateDate(new Date());
            return billRepository.save(bill);
        }
        return null;
    }

    @Override
    public List<BillDetail> getAllBillDetail() {
        return billDetailRepository.findAllByStatus(1);
    }

    @Override
    public Page<BillDetail> getAllBillDetailPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return billDetailRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public BillDetail addBillDetail(BillDetail billDetail) {
        billDetail.setStatus(1);
        return billDetailRepository.save(billDetail);
    }

    @Override
    public BillDetail updateBillDetail(BillDetail billDetail, Integer id) {
        Optional<BillDetail> optional = billDetailRepository.findById(id);
        if (optional.isPresent()) {
            BillDetail oldBillDetail = optional.get();
            billDetail.setId(oldBillDetail.getId());
            return billDetailRepository.save(billDetail);
        }
        return null;
    }

    @Override
    public Bill getOneBill(Integer id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public BillDetail getOneBillDetail(Integer id) {
        return billDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<BillDetail> getLstDetailByBillId(Integer id) {
        return billDetailRepository.findAllByBill_Id(id);
    }

    @Override
    public Bill delete(Integer id) {
        return null;
    }

    @Override
    @Transactional
    public Bill placeOrder(Cart cart, String name, String specificAddress, String ward, String district, String city, String phoneNumber, String payment, Integer voucher) {
        Bill bill = new Bill();
        bill.setCode(generateBillCode());
        bill.setType(0);
        bill.setCustomerName(name);
        String address = specificAddress + ", " + ward + ", " + district + ", " + city;
        bill.setAddress(address);
        bill.setPhoneNumber(phoneNumber);
        bill.setOrderDate(new Date());
        bill.setPrice(cart.getTotalPrice());
        if (voucher == null){
            bill.setDiscountAmount(BigDecimal.ZERO);
            bill.setTotalPrice(cart.getTotalPrice());
        } else {
            Voucher uuDai = voucherRepository.findById(voucher).orElse(null);
            bill.setDiscountAmount(uuDai.getValue());
            bill.setTotalPrice(cart.getTotalPrice().subtract(uuDai.getValue()));
            bill.setVoucher(uuDai);
            uuDai.setQuantity(uuDai.getQuantity() - 1);
            if (uuDai.getQuantity() == 0){
                uuDai.setStatus(10);
            }
            voucherRepository.save(uuDai);
        }
        bill.setShippingFee(BigDecimal.ZERO);
        bill.setCreateDate(new Date());
        bill.setUpdateDate(new Date());
        if (payment.equals("Thanh toán qua VNPAY") || payment.equals("Thanh toán qua Momo") || payment.equals("Thanh toán qua ZaloPay")){
            bill.setPaymentDate(new Date());
        }
        bill.setStatus(10);
        bill.setCustomer(cart.getCustomer());
        List<Address> listAccountAddress = addressRepository.findAllAccountAddress(cart.getCustomer().getId());
        if (listAccountAddress.isEmpty()){
            Account account = accountRepository.findById(cart.getCustomer().getId()).orElse(null);
            Address newAddress = new Address();
            newAddress.setCustomerName(name);
            newAddress.setPhoneNumber(phoneNumber);
            newAddress.setSpecificAddress(specificAddress);
            newAddress.setWard(ward);
            newAddress.setDistrict(district);
            newAddress.setCity(city);
            newAddress.setCreateDate(new Date());
            newAddress.setUpdateDate(new Date());
            newAddress.setStatus(1);
            newAddress.setAccount(account);
            addressRepository.save(newAddress);
        }

        List<BillDetail> billDetailList = new ArrayList<>();
        for (CartDetail item : cart.getCartDetails()){
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            billDetail.setProductDetail(item.getProductDetail());
            billDetail.setPrice(item.getPrice());
            billDetail.setQuantity(item.getQuantity());
            billDetail.setStatus(1);
            billDetailRepository.save(billDetail);
            billDetailList.add(billDetail);
            ProductDetail productDetail = productDetailRepository.findById(item.getProductDetail().getId()).orElse(null);
            productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
            if (productDetail.getQuantity() == 0){
                productDetail.setStatus(0);
            }
            productDetailRepository.save(productDetail);
        }

        bill.setBillDetails(billDetailList);
        cartService.deleteCartById(cart.getId());
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setBill(bill);
        paymentMethod.setName(payment);
        if (payment.equals("Thanh toán qua VNPAY") || payment.equals("Thanh toán qua Momo") || payment.equals("Thanh toán qua ZaloPay")){
            paymentMethod.setMoney(bill.getTotalPrice());
            paymentMethod.setStatus(1);
        } else {
            paymentMethod.setMoney(bill.getTotalPrice());
            paymentMethod.setStatus(10);
        }
        paymentMethod.setDescription(payment);
        paymentMethodRepository.save(paymentMethod);
        return billRepository.save(bill);
    }

    @Override
    @Transactional
    public Bill placeOrderSession(SessionCart cart, String email, String name, String specificAddress, String ward, String district, String city, String phoneNumber, String payment, Integer voucher) {
        Bill bill = new Bill();
        bill.setCode(generateBillCode());
        bill.setType(0);
        bill.setCustomerName(name);
        String address = specificAddress + ", " + ward + ", " + district + ", " + city;
        bill.setAddress(address);
        bill.setPhoneNumber(phoneNumber);
        bill.setOrderDate(new Date());
        bill.setPrice(cart.getTotalPrice());
        if (voucher == null){
            bill.setDiscountAmount(BigDecimal.ZERO);
            bill.setTotalPrice(cart.getTotalPrice());
        } else {
            Voucher uuDai = voucherRepository.findById(voucher).orElse(null);
            bill.setDiscountAmount(uuDai.getValue());
            bill.setTotalPrice(cart.getTotalPrice().subtract(uuDai.getValue()));
            bill.setVoucher(uuDai);
            uuDai.setQuantity(uuDai.getQuantity() - 1);
            if (uuDai.getQuantity() == 0){
                uuDai.setStatus(10);
            }
            voucherRepository.save(uuDai);
        }
        bill.setShippingFee(BigDecimal.ZERO);
        bill.setCreateDate(new Date());
        bill.setUpdateDate(new Date());
        if (payment.equals("Thanh toán qua VNPAY") || payment.equals("Thanh toán qua Momo") || payment.equals("Thanh toán qua ZaloPay")){
            bill.setPaymentDate(new Date());
        }
        bill.setStatus(10);
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null){
            customer = new Customer();
            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setCreateDate(new Date());
            customer.setUpdateDate(new Date());
            customer.setStatus(1);
            Role role = roleRepository.findFirstByName("User");
            customer.setRole(role);
            customer.setPassword(passwordEncoder.encode("123456"));
            customerRepository.save(customer);
        }
        bill.setCustomer(customer);
        List<Address> listAccountAddress = addressRepository.findAllAccountAddress(bill.getCustomer().getId());
        if (listAccountAddress.isEmpty()){
            Account account = accountRepository.findById(bill.getCustomer().getId()).orElse(null);
            Address newAddress = new Address();
            newAddress.setCustomerName(name);
            newAddress.setPhoneNumber(phoneNumber);
            newAddress.setSpecificAddress(specificAddress);
            newAddress.setWard(ward);
            newAddress.setDistrict(district);
            newAddress.setCity(city);
            newAddress.setCreateDate(new Date());
            newAddress.setUpdateDate(new Date());
            newAddress.setStatus(1);
            newAddress.setAccount(account);
            addressRepository.save(newAddress);
        }

        List<BillDetail> billDetailList = new ArrayList<>();
        for (SessionCartItem item : cart.getCartDetails()){
            BillDetail billDetail = new BillDetail();
            billDetail.setBill(bill);
            ProductDetail productDetail = productDetailRepository.findById(item.getProductDetail().getId()).orElse(null);
            billDetail.setProductDetail(productDetail);
            billDetail.setPrice(item.getPrice());
            billDetail.setQuantity(item.getQuantity());
            billDetail.setStatus(1);
            billDetailRepository.save(billDetail);
            billDetailList.add(billDetail);
            productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
            if (productDetail.getQuantity() == 0){
                productDetail.setStatus(0);
            }
            productDetailRepository.save(productDetail);
        }

        bill.setBillDetails(billDetailList);
        cart.clear();
        PaymentMethod paymentMethod = new PaymentMethod();
        paymentMethod.setBill(bill);
        paymentMethod.setName(payment);
        if (payment.equals("Thanh toán qua VNPAY") || payment.equals("Thanh toán qua Momo") || payment.equals("Thanh toán qua ZaloPay")){
            paymentMethod.setMoney(bill.getTotalPrice());
            paymentMethod.setStatus(1);
        } else {
            paymentMethod.setMoney(bill.getTotalPrice());
            paymentMethod.setStatus(10);
        }
        paymentMethod.setDescription(payment);
        paymentMethodRepository.save(paymentMethod);
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getNoConfirmOrders(Integer customerId) {
        return billRepository.getOrders(1, customerId);
    }

    @Override
    public List<Bill> getAllOrders(Integer customerId) {
        return billRepository.getAllOrders(customerId);
    }

    @Override
    @Transactional
    public boolean userCancelOrder(Integer billId) {
        Bill bill = billRepository.findById(billId).orElse(null);
        if (bill.getStatus() != 10){
            return false;
        } else {
            bill.setStatus(0);
            bill.setCancellationDate(new Date());
            if (bill.getVoucher() != null){
                Voucher voucher = bill.getVoucher();
                if (voucher.getStatus() == 10){
                    voucher.setQuantity(voucher.getQuantity() + 1);
                    voucher.setStatus(1);
                }
                voucherRepository.save(voucher);
            }
            List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAllByBillIdOrderById(billId);
            for (PaymentMethod paymentMethod : paymentMethodList){
                paymentMethod.setStatus(0);
            }
            List<BillDetail> billDetailList = billDetailRepository.findAllByBill_Id(billId);
            for (BillDetail billDetail : billDetailList){
                billDetail.setStatus(0);
                billDetailRepository.save(billDetail);
                ProductDetail productDetail = productDetailRepository.findById(billDetail.getProductDetail().getId()).orElse(null);
                productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity());
                productDetail.setStatus(1);
                productDetailRepository.save(productDetail);
            }
            bill.setBillDetails(billDetailList);
            billRepository.save(bill);
            return true;
        }
    }

    @Override
    public boolean confirmOrder(BigDecimal shippingFee, Integer id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill.getStatus() != 10){
            return false;
        } else {
            bill.setStatus(3);
            bill.setConfirmationDate(new Date());
            bill.setShippingFee(shippingFee);
            bill.setTotalPrice(bill.getPrice().subtract(bill.getDiscountAmount()).add(shippingFee));
            PaymentMethod paymentMethod = paymentMethodRepository.findByStatusAndBillId(10, id);
            if (paymentMethod != null){
                paymentMethod.setMoney(bill.getTotalPrice());
                paymentMethodRepository.save(paymentMethod);
            } else if (shippingFee.compareTo(BigDecimal.ZERO) != 0){
                PaymentMethod newPaymentMethod = new PaymentMethod();
                newPaymentMethod.setName("Thanh toán khi nhận hàng");
                newPaymentMethod.setMoney(shippingFee);
                newPaymentMethod.setDescription("Thanh toán khi nhận hàng");
                newPaymentMethod.setStatus(10);
                newPaymentMethod.setBill(bill);
                paymentMethodRepository.save(newPaymentMethod);
            }
            billRepository.save(bill);
            return true;
        }
    }

    @Override
    public boolean shippingOrder(Integer id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill.getStatus() != 3){
            return false;
        } else {
            bill.setStatus(2);
            bill.setShippingDate(new Date());
            billRepository.save(bill);
            return true;
        }
    }

    @Override
    public boolean completeOrder(Integer id) {
        Bill bill = billRepository.findById(id).orElse(null);
        if (bill.getStatus() != 2){
            return false;
        } else {
            bill.setStatus(1);
            bill.setCompletionDate(new Date());
            List<PaymentMethod> listPaymentMethod = paymentMethodRepository.findAllByBillIdOrderById(id);
            for (PaymentMethod paymentMethod : listPaymentMethod) {
                if (paymentMethod.getStatus() == 10){
                    paymentMethod.setStatus(1);
                    paymentMethodRepository.save(paymentMethod);
                }
            }
            billRepository.save(bill);
            return true;
        }
    }

    @Override
    @Transactional
    public boolean cancelOrder(Integer billId){
        Bill bill = billRepository.findById(billId).orElse(null);
        if (bill.getStatus() == 0){
            return false;
        } else {
            bill.setStatus(0);
            bill.setCancellationDate(new Date());
            if (bill.getVoucher() != null){
                Voucher voucher = bill.getVoucher();
                if (voucher.getStatus() == 10){
                    voucher.setQuantity(voucher.getQuantity() + 1);
                    voucher.setStatus(1);
                }
                voucherRepository.save(voucher);
            }
            List<PaymentMethod> paymentMethodList = paymentMethodRepository.findAllByBillIdOrderById(billId);
            for (PaymentMethod paymentMethod : paymentMethodList){
                paymentMethod.setStatus(0);
                paymentMethodRepository.save(paymentMethod);
            }
            List<BillDetail> billDetailList = billDetailRepository.findAllByBill_Id(billId);
            for (BillDetail billDetail : billDetailList){
                billDetail.setStatus(0);
                billDetailRepository.save(billDetail);
                ProductDetail productDetail = productDetailRepository.findById(billDetail.getProductDetail().getId()).orElse(null);
                productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity());
                productDetail.setStatus(1);
                productDetailRepository.save(productDetail);
            }
            bill.setBillDetails(billDetailList);
            billRepository.save(bill);
            return true;
        }
    }

    public String generateBillCode() {
        Integer lastId = 0;
        Bill lastBill = billRepository.findTopByOrderByIdDesc();
        if (lastBill != null) {
            lastId = lastBill.getId();
        }
        return String.format("HD%06d", lastId + 1);
    }

    @Override
    public Page<BillDto> findAll(Pageable pageable){
        return billRepository.listBill(pageable);
    }

    @Override
    public Page<BillDto> searchListBill(String code, Date ngayTaoStart, Date ngayTaoEnd, Integer status, Integer type, String phoneNumber, String customerName, Pageable pageable) {
        return billRepository.listSearchBill(code, ngayTaoStart, ngayTaoEnd, status, type, phoneNumber, customerName, pageable);
    }
}
