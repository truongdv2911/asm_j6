package com.example.demo.Service;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderDetail;
import com.example.demo.Responses.OrderDetailResponse;
import com.example.demo.Responses.OrderResponse;

import java.util.List;

public interface IOrderDetail {
    OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;
    OrderDetailResponse findById(Integer id) throws Exception;
    List<OrderDetailResponse> getAll(Integer id);

    OrderDetail updateOrder(Integer id, OrderDetailDTO orderDetailDTO) throws Exception;
    void deleteOrderDetail(Integer id) throws Exception;
}
