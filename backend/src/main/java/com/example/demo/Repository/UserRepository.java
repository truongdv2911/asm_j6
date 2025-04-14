package com.example.demo.Repository;

import com.example.demo.Model.Product;
import com.example.demo.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsBySdt(String sdt);
    Optional<User> findBySdt(String sdt);
}
