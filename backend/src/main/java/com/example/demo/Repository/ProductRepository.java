package com.example.demo.Repository;

import com.example.demo.Model.Product;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsByName(String name);

    @Query("select p from Product p where :categoryID is null or :categoryID = 0 or p.category.id = :categoryID and" +
            " (:keyword is null or :keyword = '' or p.name like %:keyword% or p.description like %:keyword%)")
    Page<Product> searchProduct(@Param("categoryID") Integer categoryID,
                                @Param("keyword") String  keyword,
                                Pageable pageable
                                );

    @Query("select p from Product p where p.id in :productIds")
    List<Product> findByIds(@Param("productIds") List<Integer> productIds);
}
