package com.btvn.ss16.config;

import com.btvn.ss16.model.Category;
import com.btvn.ss16.model.Product;
import com.btvn.ss16.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        Long count = entityManager
                .createQuery("select count(c) from Category c", Long.class)
                .getSingleResult();

        if (count > 0) {
            return;
        }

        Category electronics = persistCategory("Điện tử", "Thiết thiết bị điện tử, phụ kiện");
        Category fashion = persistCategory("Thời trang", "Quần áo, giày dép");

        persistProduct("Laptop Dell XPS 15", Double.valueOf("25990000"), 12,
                "Laptop cao cấp 15 inch", electronics,
                "https://lapvip.vn/upload/products/thumb_350x0/dell-xps-16-9640-2024-1706929809.jpg");

        persistProduct("Tai nghe Sony WH-1000XM5", Double.valueOf("7990000"), 30,
                "Tai nghe chống ồn", electronics,
                "https://bizweb.dktcdn.net/100/451/485/products/bose-quietcomfort-ultra-28.jpg?v=1764673266237");

        User demo = User.builder()
                .fullName("Nguyễn Văn A")
                .email("A@example.com")
                .phone("0901234567")
                .address("123 Đường ABC, Quận 1, TP.HCM")
                .build();
        entityManager.persist(demo);
    }

    private Category persistCategory(String name, String description) {
        Category c = Category.builder()
                .name(name)
                .description(description)
                .build();
        entityManager.persist(c);
        return c;
    }

    private void persistProduct(String name, Double price, int stock, String description,
                                Category category, String imageUrl) {
        Product p = Product.builder()
                .name(name)
                .price(price)
                .stockQuantity(stock)
                .description(description)
                .imageUrl(imageUrl)
                .category(category)
                .build();
        entityManager.persist(p);
    }
}