package com.example.demo.Service;

import com.example.demo.DTO.CartItemDto;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.*;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService implements IOrder{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
         try {
             User user = userRepository.findById(orderDTO.getId_user()).orElseThrow(() -> new Exception("khong tim thay nguoi dung"));
             modelMapper.typeMap(OrderDTO.class, Order.class)
                     .addMappings(mapper -> mapper.skip(Order::setId));
             Order order = new Order();
             modelMapper.map(orderDTO, order);
             order.setUser(user);
             order.setOrder_date(new Date());
             order.setIs_active(1);
             order.setStatus(OrderStatus.PENDING);
             orderRepository.save(order);

             List<OrderDetail> orderDetails = new ArrayList<>();
             for (CartItemDto cartItemDto:
                     orderDTO.getCartItems()) {
                 OrderDetail orderDetail = new OrderDetail();
                 orderDetail.setOrder(order);

                 Integer productId = cartItemDto.getId_product();
                 Integer quantity = cartItemDto.getQuantity();
                 Product product = productRepository.findById(productId).
                         orElseThrow(() -> new Exception("Khong tim thay san pham"));
                 Float price = product.getPrice();
                 orderDetail.setProduct(product);
                 orderDetail.setPrice(price);
                 orderDetail.setQuality(quantity);
                 orderDetail.setTotal(price*quantity);
                 orderDetails.add(orderDetail);
             }
             orderDetailRepository.saveAll(orderDetails);
             return order;
         }catch (Exception e){
             throw new Exception(e.getMessage());
         }
    }

    @Override
    public OrderResponse findById(Integer id) throws Exception {
        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getId(), OrderResponse::setUser));
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("khong tim thay hoa don"));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getAll(Integer user_id) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Order.class, OrderResponse.class).addMappings(mapper ->
                mapper.map(src -> src.getUser().getId(), OrderResponse::setUser));
        return orderRepository.findByIdUser(user_id).stream().map(order -> {
            OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
            return orderResponse;
        }).toList();
    }

    @Override
    @Transactional
    public Order updateOrder(Integer id, OrderDTO orderDTO) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("khong tim thay hoa don"));
        User user = userRepository.findById(orderDTO.getId_user()).orElseThrow(() -> new Exception("khong tim thay nguoi dung"));
        modelMapper.typeMap(OrderDTO.class, Order.class).addMappings(mapper ->
                mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer id) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(() -> new Exception("khong tim thay hoa don"));
        order.setIs_active(2);
        orderRepository.save(order);
    }
}
