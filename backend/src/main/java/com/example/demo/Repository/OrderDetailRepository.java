package com.example.demo.Repository;

import com.example.demo.Model.Order;
import com.example.demo.Model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
    @Query("select o from OrderDetail o where o.order.id = :id ")
    List<OrderDetail> findByIdOrder(Integer id);
}
