package com.example.demo.Service;

import com.example.demo.Components.JwtTokenUntil;
import com.example.demo.DTO.UserDTO;
import com.example.demo.DTO.UserLoginDTO;
import com.example.demo.Model.Role;
import com.example.demo.Model.User;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUser{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUntil jwtTokenUntil;
    private final AuthenticationManager authenticationManager;
    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
            String sdt = userDTO.getSdt();
            if (userRepository.existsBySdt(sdt)){
                throw new DataIntegrityViolationException("So dien thoai da ton tai");
            }
            User user = new User(null,userDTO.getFull_name(),userDTO.getSdt(),userDTO.getAddress(),
                    userDTO.getPassword(),1,userDTO.getDate_of_birth(),userDTO.getFacebook_id(),
                    userDTO.getGoogle_id(),roleRepository.findById(userDTO.getId_role()).orElseThrow(() ->
                    new Exception("Nhap sai role")));
            if(userDTO.getGoogle_id() == 0 && userDTO.getFacebook_id() == 0){
                String password = userDTO.getPassword();
                String encodePassword = passwordEncoder.encode(password);
                user.setPassword(encodePassword);
            }
            return userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO userLoginDTO) throws Exception {
        Optional<User> user = userRepository.findBySdt(userLoginDTO.getSdt());
        if (user.isEmpty()){
            throw new Exception("Sai thong tin dang nhap");
        }
        if(user.get().getGoogle_id() == 0 && user.get().getFacebook_id() == 0){
            if(!passwordEncoder.matches(userLoginDTO.getPassword(), user.get().getPassword())){
                throw new BadCredentialsException("Sai thong tin");
            }
        }
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDTO.getSdt(), userLoginDTO.getPassword(), user.get().getAuthorities());
        authenticationManager.authenticate(usernamePassword);
        return jwtTokenUntil.generationToken(user.get());
    }

    @Override
    public User detailUser(String token) throws Exception {
        if (jwtTokenUntil.isTokenExpired(token)){
            throw new Exception("token is expired");
        }
        String sdt = jwtTokenUntil.extractPhone(token);
        Optional<User> user = userRepository.findBySdt(sdt);
        if (user.isPresent()){
            return user.get();
        }else{
            throw new Exception("User not found");
        }
    }
}
