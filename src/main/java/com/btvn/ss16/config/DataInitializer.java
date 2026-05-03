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

        Category electronics = persistCategory("Điện tử", "Thiết bị điện tử, phụ kiện");
        Category fashion = persistCategory("Thời trang", "Quần áo, giày dép");

        persistProduct("Laptop Dell XPS 15", 25_990_000L, 12,
                "Laptop cao cấp 15 inch", electronics,
                "https://placehold.co/400x300?text=Laptop");
        persistProduct("Tai nghe Sony WH-1000XM5", 7_990_000L, 30,
                "Tai nghe chống ồn", electronics,
                "https://placehold.co/400x300?text=Headphone");
        persistProduct("Chuột Logitech MX Master 3", 2_490_000L, 45,
                "Chuột không dây", electronics, null);

        persistProduct("Áo thun nam Basic", 199_000L, 100,
                "Cotton 100%", fashion, null);
        persistProduct("Giày sneaker Unisex", 890_000L, 60,
                "Size 36-43", fashion, null);

        User demo = User.builder()
                .fullName("Nguyễn Văn Demo")
                .email("demo.customer@example.com")
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

    private void persistProduct(String name, Long price, int stock, String description,
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
