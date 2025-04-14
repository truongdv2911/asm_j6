package com.example.demo.Responses;

import com.example.demo.Model.BaseEntity;
import com.example.demo.Model.User;
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
public class UserResponse extends BaseEntity {
    private Integer id;
    private String full_name;
    private String sdt;
    private String address;
    private Date date_of_birth;
    private int facebook_id;
    private int google_id;
    private String role_name;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFull_name(),
                user.getSdt(),
                user.getAddress(),
                user.getDate_of_birth(),
                user.getFacebook_id(),
                user.getGoogle_id(),
                user.getRole().getName()
        );
        userResponse.setCreateAt(user.getCreateAt());
        return userResponse;
    }
}
