package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImgDTO;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductImg;
import com.example.demo.Responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProduct {
    Product createProduct(ProductDTO productDTO);
    Product findById(Integer id);
    List<Product> getAll();
    Product updateProduct(Integer id, ProductDTO productDTO);
    void deleteProduct(Integer id);
    ProductImg createProductImg(Integer id,ProductImgDTO productImgDTO);
    List<ProductImgDTO> getImgs(Integer idProduct);
    Boolean existsByName(String name);
    Page<ProductResponse> getALlPage(String keyword, Integer categoryID, PageRequest pageRequest);
    List<Product> findByProductIds(List<Integer> ids);
}
