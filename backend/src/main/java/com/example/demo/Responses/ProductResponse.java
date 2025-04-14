package com.example.demo.Responses;

import com.example.demo.Model.BaseEntity;
import com.example.demo.Model.Product;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse extends BaseEntity {
    private Integer id;
    private String name;
    private Float price;
    private String thumnail;
    private String description;
    private Integer category_id;

    public static ProductResponse fromProduct(Product product){
        ProductResponse productResponse = new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getThumnail(),product.getDescription()
                ,product.getCategory().getId());
        productResponse.setCreateAt(product.getCreateAt());
        productResponse.setUpdateAt(product.getUpdateAt());
        return productResponse;
    }
}
