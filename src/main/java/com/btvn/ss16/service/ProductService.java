package com.btvn.ss16.service;

import com.btvn.ss16.model.Category;
import com.btvn.ss16.model.Product;
import com.btvn.ss16.repository.ProductRepository;
import com.btvn.ss16.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private static final String UPLOAD_DIR = "uploads/";

    // tạo mới, update
    public Product save(Product product, MultipartFile file) throws IOException {

        if (file != null && !file.isEmpty()) {

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) dir.mkdirs();

            file.transferTo(new File(UPLOAD_DIR + fileName));

            product.setImageUrl(fileName);
        }

        return productRepository.save(product);
    }

    // xoá
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    // tìm kiếm
    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(
                PageRequest.of(page, size, Sort.by("id").descending())
        );
    }

    // phân trang tìm kiếm
    public Page<Product> search(
            String name,
            Double minPrice,
            Double maxPrice,
            Long categoryId,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Specification<Product> spec = Specification.where(null);
        spec = spec.and(ProductSpecification.hasName(name));
        spec = spec.and(ProductSpecification.hasMinPrice(minPrice));
        spec = spec.and(ProductSpecification.hasMaxPrice(maxPrice));
        spec = spec.and(ProductSpecification.hasCategory(categoryId));
        return productRepository.findAll(spec, pageable);
    }

    // lấy danh sách sản phẩm theo category
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    // đếm số sản phẩm trong category
    public long countByCategory(Category category) {
        return productRepository.countByCategory(category);
    }

    // optional
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
