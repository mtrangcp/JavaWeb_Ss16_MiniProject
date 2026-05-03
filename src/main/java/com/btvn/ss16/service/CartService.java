package com.btvn.ss16.service;

import com.btvn.ss16.dto.CartItemDto;
import com.btvn.ss16.model.Product;
import com.btvn.ss16.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private ProductRepository productRepo;

    // Thêm sản phẩm
    public void add(Map<Long, CartItemDto> cart, Long id) {
        if (cart.containsKey(id)) {
            CartItemDto item = cart.get(id);
            item.setQuantity(item.getQuantity() + 1);
        } else {
            Product p = productRepo.findById(id).orElseThrow();
            cart.put(id, new CartItemDto(p.getId(), p.getName(), p.getPrice(), 1));
        }
    }

    // Cập nhật số lượng
    public void update(Map<Long, CartItemDto> cart, Long id, Integer qty) {
        if (cart.containsKey(id) && qty > 0) {
            cart.get(id).setQuantity(qty);
        }
    }

    // Tính tổng tiền
    public double getTotal(Map<Long, CartItemDto> cart) {
        return cart.values().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
    }
}