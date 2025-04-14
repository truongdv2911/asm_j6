package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id_user;
    @NotBlank
    private String fullname;
    @NotBlank
    private String sdt;
    @NotBlank
    private String address;
    @Min(value = 0, message = "gia phai lon hon khong")
    private Float total;
    private List<CartItemDto> cartItems;
}
