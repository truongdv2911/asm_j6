package com.example.demo.Controller;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Responses.CategoryResponse;
import com.example.demo.Service.CateService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController{
    private final CateService cateService;

    @GetMapping("")
    public ResponseEntity<?> getAll(
//            @RequestParam("page") int pageNo,
//            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok(cateService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){

        return ResponseEntity.ok(cateService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CategoryDTO cate,
                                           BindingResult result
                                           ){
        if (result.hasErrors()){
            List<String> listErorrs = result.getFieldErrors().stream().
                    map(errors -> errors.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(listErorrs);
        }
        cateService.createCate(cate);
        return ResponseEntity.ok(new CategoryResponse("Them thanh cong"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCate(@PathVariable Integer id,@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){
        if (result.hasErrors()){
            List<String> listErorrs = result.getFieldErrors().stream().
                    map(errors -> errors.getDefaultMessage()).toList();
            return ResponseEntity.badRequest().body(listErorrs);
        }
        cateService.updateCate(id,categoryDTO);
        return ResponseEntity.ok(new CategoryResponse("Sua thanh cong"));
    }
}
