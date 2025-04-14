package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Integer id_order;
    private Integer id_product;
    @Min(value = 0, message = "gia phai lon hon 0")
    private Float price;
    @Min(value = 1)
    private Integer quality;
    private Float total;
}
