package com.example.demo.Repository;

import com.example.demo.Model.Order;
import com.example.demo.Responses.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.user.id = :id ")
    List<Order> findByIdUser(Integer id);
}
