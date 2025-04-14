package com.example.demo.Service;

import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Model.Order;
import com.example.demo.Model.OrderDetail;
import com.example.demo.Model.Product;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Responses.OrderDetailResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetail{
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Order order = orderRepository.findById(orderDetailDTO.getId_order()).orElseThrow(() ->
                new Exception("Khong thay hoa don"));
        Product product = productRepository.findById(orderDetailDTO.getId_product()).orElseThrow(() ->
                new Exception("Khong tim thay san pham"));
        OrderDetail orderDetail = new OrderDetail(null,order,product,orderDetailDTO.getPrice()
        ,orderDetailDTO.getQuality(),orderDetailDTO.getTotal());
        orderDetailRepository.save(orderDetail);
        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse findById(Integer id) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() ->  new Exception("Khong thay san pham"));
        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public List<OrderDetailResponse> getAll(Integer idOrder) {
        ModelMapper modelMapper = new ModelMapper();
        return orderDetailRepository.findByIdOrder(idOrder).stream().map(orderDetail -> {
            OrderDetailResponse orderDetailResponse = modelMapper.map(orderDetail,OrderDetailResponse.class);
            return orderDetailResponse;
        }).toList();
    }

    @Override
    @Transactional
    public OrderDetail updateOrder(Integer id, OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getId_order()).orElseThrow(() ->
                new Exception("Khong thay hoa don"));
        Product product = productRepository.findById(orderDetailDTO.getId_product()).orElseThrow(() ->
                new Exception("Khong tim thay san pham"));
        OrderDetail orderDetail = orderDetailRepository.save(new OrderDetail(id, order,product,orderDetailDTO.getPrice()
                ,orderDetailDTO.getQuality(),orderDetailDTO.getTotal()));
        return orderDetail;
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Integer id) throws Exception {
        orderDetailRepository.deleteById(id);
    }
}
