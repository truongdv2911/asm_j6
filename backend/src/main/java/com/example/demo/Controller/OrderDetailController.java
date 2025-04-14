package com.example.demo.Controller;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.OrderDetailDTO;
import com.example.demo.Service.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("api/orderDetail")
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDetailDTO order_detail, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            return ResponseEntity.ok(orderDetailService.createOrderDetail(order_detail));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneWithId(@PathVariable Integer id) throws Exception {
        return ResponseEntity.ok(orderDetailService.findById(id));
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getListOrderDetailWithIdOrder(@PathVariable Integer id){
        return ResponseEntity.ok(orderDetailService.getAll(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Integer id, @Valid
    @RequestBody OrderDetailDTO orderDetailDTO, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            orderDetailService.updateOrder(id, orderDetailDTO);
            return ResponseEntity.ok("Update thanh cong");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("Co loi");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) throws Exception {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }
}
