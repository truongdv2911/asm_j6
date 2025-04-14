package com.example.demo.Controller;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.DTO.ProductDTO;
import com.example.demo.DTO.ProductImgDTO;
import com.example.demo.Model.Product;
import com.example.demo.Model.ProductImg;
import com.example.demo.Responses.ProductListResponse;
import com.example.demo.Responses.ProductResponse;
import com.example.demo.Service.ProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<ProductListResponse> getAll(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "categoryID") Integer categoryID,
            @RequestParam("page") int pageNo,
           @RequestParam("limit") int limit
    ){
        PageRequest pageRequest = PageRequest.of(pageNo, limit, Sort.by("createAt").ascending());
        Page<ProductResponse> products = productService.getALlPage(keyword, categoryID, pageRequest);

        int totalPage = products.getTotalPages();
        List<ProductResponse> listProduct = products.getContent();
        return ResponseEntity.ok(new ProductListResponse(listProduct,totalPage));
    }

    @GetMapping("/img/{id}")
    public ResponseEntity<?> getListImgs(@PathVariable Integer id){
        try {
            return ResponseEntity.ok(productService.getImgs(id));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return ResponseEntity.ok(ProductResponse.fromProduct(productService.findById(id)));
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO product,
                                           BindingResult result
    ) throws IOException {
        if (result.hasErrors()){
            List<String> listErorrs = result.getFieldErrors().stream().
                    map(errors -> errors.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(listErorrs);
        }
        Product productNew = productService.createProduct(product);
        return ResponseEntity.ok(productNew);
    }
    @PostMapping(value = "upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImgs(@RequestParam("files") List<MultipartFile> files,
                                        @PathVariable Integer id){

        try {
            Product product = productService.findById(id);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() >5){
                return ResponseEntity.badRequest().body("Chi upload dc toi da 5 anh");
            }
            List<ProductImg> productImgs = new ArrayList<>();
            for (MultipartFile file : files){
                if(file!=null){
                    if (file.getSize() == 0){
                        continue;
                    }
                    if(file.getSize() > 10*1024*1024){
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("file qua lon");
                    }
                }
                String filename = storeFile(file);
                ProductImg productImg = productService.createProductImg(product.getId(), new ProductImgDTO(filename));
                productImgs.add(productImg);
            }
            return ResponseEntity.ok(productImgs);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/images/{imgName}")
    public ResponseEntity<?> viewImage(@PathVariable String imgName){
        try {
            Path imgPath = Paths.get("uploads/"+imgName);
            UrlResource resource = new UrlResource(imgPath.toUri());

            if (resource.exists()){
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    private String storeFile(MultipartFile file)throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String uniquename = Math.random()+"_"+filename;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniquename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniquename;
    }

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductByIds(@RequestParam("ids") String ids){
        try {
            List<Integer> productIds = Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productService.findByProductIds(productIds));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDTO product,
                                           BindingResult result
    ){
        if (result.hasErrors()){
            List<String> listErorrs = result.getFieldErrors().stream().
                    map(errors -> errors.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(listErorrs);
        }
        return ResponseEntity.ok("them thanh cong");
    }

    @DeleteMapping()
    public ResponseEntity<?> delete(@PathVariable Integer id){
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xoa thanh cong");
    }

    //PostMapping("/fakeProducts")
    public ResponseEntity<String> faker(){
        Faker faker = new Faker();
        for (int i = 0; i < 500; i++) {
            String name = faker.commerce().productName();
            if (productService.existsByName(name)){
                continue;
            }
            productService.createProduct(new ProductDTO(name,(float)faker.number().numberBetween(200000,10000000)
            ,"",faker.lorem().characters(), faker.number().numberBetween(1,4)));
        }
        return ResponseEntity.ok("");
    }
}
