package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Model.User;
import com.example.demo.Responses.LoginResponse;
import com.example.demo.Responses.UserResponse;
import com.example.demo.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO user, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            return ResponseEntity.ok(userService.createUser(user));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO user, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> listErorrs = result.getFieldErrors().stream().
                        map(errors -> errors.getDefaultMessage()).toList();
                return ResponseEntity.badRequest().body(listErorrs);
            }
            String token = userService.login(user);
            return ResponseEntity.ok(new LoginResponse(token,"Dang nhap thanh cong "));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new LoginResponse(null,"Sai thong tin dang nhap"));
        }
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestHeader("Authorization") String token){
        try {
            String extractedToken = token.substring(7);
            User user = userService.detailUser(extractedToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
