package com.example.demo.Service;

import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Model.User;

public interface IUser {
    User createUser(UserDTO userDTO) throws Exception;
    String login(UserLoginDTO userLoginDTO) throws Exception;
    User detailUser(String token) throws Exception;
}
