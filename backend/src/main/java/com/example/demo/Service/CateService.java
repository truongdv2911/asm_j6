package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Model.Category;
import com.example.demo.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CateService implements ICategory{
    private final CategoryRepository categoryRepository;

    @Override
    public Category createCate(CategoryDTO categoryDTO) {
        return categoryRepository.save(new Category(null,categoryDTO.getName()));
    }

    @Override
    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCate(Integer id, CategoryDTO categoryDTO) {
        return categoryRepository.save(new Category(id, categoryDTO.getName()));
    }

    @Override
    public void deleteCate(Integer id) {
        categoryRepository.deleteById(id);
    }
}
