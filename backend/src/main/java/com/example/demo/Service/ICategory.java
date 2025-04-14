package com.example.demo.Service;

import com.example.demo.DTO.CategoryDTO;
import com.example.demo.Model.Category;
import com.example.demo.Repository.CategoryRepository;

import java.util.List;

public interface ICategory {
    Category createCate(CategoryDTO categoryDTO);
    Category findById(Integer id);
    List<Category> getAll();

    Category updateCate(Integer id, CategoryDTO categoryDTO);
    void deleteCate(Integer id);
}
