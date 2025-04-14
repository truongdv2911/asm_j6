package com.example.demo.Responses;

import com.example.demo.Model.BaseEntity;
import com.example.demo.Model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private Integer user;
    private String fullname;
    private String sdt;
    private String address;
    private Date order_date;
    private String status;
    private Float total;
    private Integer is_active;
}
