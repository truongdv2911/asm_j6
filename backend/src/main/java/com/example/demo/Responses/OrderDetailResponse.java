package com.example.demo.Responses;

import com.example.demo.Model.Order;
import com.example.demo.Model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class OrderDetailResponse {
    private Integer id;
    private Integer orderID;
    private Integer productID;
    private Float price;
    private Integer quality;
    private Float total;
}
