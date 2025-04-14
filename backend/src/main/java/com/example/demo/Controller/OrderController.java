package com.example.demo.Controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.Model.Order;
import com.example.demo.Responses.OrderResponse;
import com.example.demo.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO order, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            return ResponseEntity.ok(orderService.createOrder(order));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getAll(@PathVariable Integer user_id){
        try {
            List<OrderResponse> listOrder = orderService.getAll(user_id);
            return ResponseEntity.ok(listOrder);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        try {
            OrderResponse order = orderService.findById(id);
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Integer id, @Valid
                                         @RequestBody OrderDTO order, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            return ResponseEntity.ok(orderService.updateOrder(id, order));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@Valid@PathVariable Integer id) throws Exception {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }
}
