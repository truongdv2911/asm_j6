package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String full_name;
    @NotBlank(message = "Khong de trong sdt")
    private String sdt;
    private String address;
    @NotBlank
    private String password;
    private Date date_of_birth;
    private int facebook_id;
    private int google_id;
    @NotNull
    private Integer id_role;
}
