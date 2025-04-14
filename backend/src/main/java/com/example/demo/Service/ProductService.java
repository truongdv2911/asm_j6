package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImgDTO;
import com.example.demo.Model.Category;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductImg;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ProductImgRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Responses.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class ProductService implements IProduct{
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImgRepository productImgRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategory_id()).orElseThrow(() ->
                new RuntimeException("Cannot find categoru id"));
        Product product = productRepository.save(new Product(null, productDTO.getName(),productDTO.getPrice(),
                productDTO.getThumnail(),productDTO.getDescription(), category));
        return product;
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product updateProduct(Integer id, ProductDTO productDTO) {
        return productRepository.save(new Product(id,productDTO.getName(),productDTO.getPrice(),
                productDTO.getThumnail(),productDTO.getDescription(),categoryRepository.findById(productDTO.getCategory_id()).orElse(null)));
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<ProductResponse> getALlPage(String keyword, Integer categoryID, PageRequest pageRequest) {
        return productRepository.searchProduct(categoryID,keyword,pageRequest).map(product -> {
            ProductResponse productResponse = new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getThumnail(),product.getDescription()
                    ,product.getCategory().getId());
            productResponse.setCreateAt(product.getCreateAt());
            productResponse.setUpdateAt(product.getUpdateAt());
            return productResponse;
        });
    }

    @Override
    public List<Product> findByProductIds(List<Integer> productIds) {
        return productRepository.findByIds(productIds);
    }

    @Override
    @Transactional
    public ProductImg createProductImg(Integer id,ProductImgDTO productImgDTO){
        Product product = productRepository.findById(id).orElse(null);
        int size = productImgRepository.findByProduct_id(product.getId()).size();
        if (size>=5){
            return null;
        }
        return productImgRepository.save(new ProductImg(null, productImgDTO.getUrl_img(),product));
    }

    @Override
    public List<ProductImgDTO> getImgs(Integer idProduct) {
        ModelMapper modelMapper = new ModelMapper();
        List<ProductImg> productImgs = productImgRepository.findByProduct_id(idProduct);
        return productImgs.stream().map(img -> {
            ProductImgDTO productImgDTO = modelMapper.map(img,ProductImgDTO.class);
            return productImgDTO;
        }).toList();
    }

    @Override
    public Boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
