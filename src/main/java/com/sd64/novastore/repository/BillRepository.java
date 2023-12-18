package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.BillDto;
import com.sd64.novastore.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findAllByStatus(Integer status);

    Page<Bill> findAllByStatus(Pageable pageable, Integer status);

    Bill findTopByOrderByIdDesc();

    @Query("SELECT b FROM Bill b WHERE b.status = :status AND b.customer.id = :customerId ORDER BY b.orderDate DESC")
    List<Bill> getOrders(@Param("status") Integer status, @Param("customerId") Integer customerId);

    @Query("SELECT b FROM Bill b WHERE b.customer.id = :customerId ORDER BY b.orderDate DESC")
    List<Bill> getAllOrders(@Param("customerId") Integer customerId);

    @Query(value = "SELECT b.id AS id, b.code AS code, b.customerName AS customerName, b.phoneNumber " +
            "            AS phoneNumber, b.orderDate AS createDate, b.totalPrice AS totalPrice, b.type " +
            "            AS type, b.status AS status" +
            "            FROM Bill b")
    Page<BillDto> listBill(Pageable pageable);

    @Query(value = "SELECT b.id AS id,b.code AS code, b.customerName AS customerName, b.phoneNumber " +
            "            AS phoneNumber,b.orderDate AS createDate, b.totalPrice AS totalPrice, b.type " +
            "            AS type, b.status AS status" +
            "            FROM Bill b " +
            "WHERE (:code IS NULL OR b.code LIKE CONCAT('%', :code, '%')) " +
            "AND (:ngayTaoStart IS NULL OR b.orderDate >= :ngayTaoStart) " +
            "AND (:ngayTaoEnd IS NULL OR b.orderDate <= :ngayTaoEnd)" +
            "AND (:status IS NULL OR b.status = :status) " +
            "AND (:type IS NULL OR b.type= :type) "+
            "AND (:phoneNumber IS NULL OR b.phoneNumber IS NULL OR b.phoneNumber LIKE CONCAT('%', :phoneNumber, '%')) "+
            "AND (:customerName IS NULL OR b.customerName IS NULL OR b.customerName LIKE CONCAT('%', :customerName, '%'))")
    Page<BillDto> listSearchBill(
            @Param("code") String code,
            @Param("ngayTaoStart") Date ngayTaoStart,
            @Param("ngayTaoEnd") Date ngayTaoEnd,
            @Param("status") Integer status,
            @Param("type") Integer type,
            @Param("phoneNumber") String phoneNumber,
            @Param("customerName") String customerName,
            Pageable pageable);
}
