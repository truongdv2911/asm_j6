package com.example.demo.Repository;

import com.example.demo.Model.Product;
import com.example.demo.Model.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {
    List<ProductImg> findByProduct_id(Integer product_id);
}
