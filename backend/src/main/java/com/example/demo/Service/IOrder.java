package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.Category;
import com.example.demo.Model.Order;
import com.example.demo.Responses.OrderResponse;

import java.util.List;

public interface IOrder {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    OrderResponse findById(Integer id) throws Exception;
    List<OrderResponse> getAll(Integer id);

    Order updateOrder(Integer id, OrderDTO orderDTO) throws Exception;
    void deleteOrder(Integer id) throws Exception;
}
