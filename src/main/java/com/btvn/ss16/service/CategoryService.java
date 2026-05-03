package com.btvn.ss16.service;

import com.btvn.ss16.exception.BadRequestException;
import com.btvn.ss16.exception.ResourceNotFoundException;
import com.btvn.ss16.model.Category;
import com.btvn.ss16.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy tất cả category
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.isEmpty() ? Collections.emptyList() : categories;
    }

    // Lấy category theo ID
    public Category getCategoryById(Long id) {

        if (id == null) {
            throw new BadRequestException("ID không được null");
        }

        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy Category với id = " + id));
    }

    // Tạo mới category
    public Category create(Category category) {

        validateCategory(category);

        return categoryRepository.save(category);
    }

    // Update category
    public Category update(Category category) {

        if (category == null || category.getId() == null) {
            throw new BadRequestException("Category hoặc ID không hợp lệ");
        }

        Category existing = categoryRepository.findById(category.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy Category với id = " + category.getId()));

        // cập nhật dữ liệu
        existing.setName(category.getName());

        return categoryRepository.save(existing);
    }

    // Xóa category
    public void delete(Long id) {

        if (id == null) {
            throw new BadRequestException("ID không được null");
        }

        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy Category với id = " + id);
        }

        categoryRepository.deleteById(id);
    }

    // Tìm kiếm theo tên
    public List<Category> getCategoriesByName(String name) {

        if (name == null || name.isBlank()) {
            throw new BadRequestException("Tên tìm kiếm không hợp lệ");
        }

        List<Category> result = categoryRepository
                .findByNameContainingIgnoreCase(name.trim());

        if (result.isEmpty()) {
            throw new ResourceNotFoundException(
                    "Không tìm thấy category nào với tên: " + name);
        }

        return result;
    }

    // Validate dùng chung
    private void validateCategory(Category category) {

        if (category == null) {
            throw new BadRequestException("Category không được null");
        }

        if (category.getName() == null || category.getName().isBlank()) {
            throw new BadRequestException("Tên category không được để trống");
        }
    }
}

