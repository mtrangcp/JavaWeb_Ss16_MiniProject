package com.btvn.ss16.controller;

import com.btvn.ss16.dto.CartItemDto;
import com.btvn.ss16.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired private CartService cartService;

    @GetMapping
    public String view() { return "client/cart"; }

    @GetMapping("/add/{id}")
    public String add(@PathVariable Long id, HttpSession session) {
        Map<Long, CartItemDto> cart = getCart(session);
        cartService.add(cart, id);
        updateSession(session, cart);
        return "redirect:/cart";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestParam("qty") Integer qty, HttpSession session) {
        Map<Long, CartItemDto> cart = getCart(session);
        cartService.update(cart, id, qty);
        updateSession(session, cart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id, HttpSession session) {
        Map<Long, CartItemDto> cart = getCart(session);
        cart.remove(id);
        updateSession(session, cart);
        return "redirect:/cart";
    }

    private Map<Long, CartItemDto> getCart(HttpSession session) {
        Map<Long, CartItemDto> cart = (Map<Long, CartItemDto>) session.getAttribute("cart");
        return (cart != null) ? cart : new HashMap<>();
    }

    private void updateSession(HttpSession session, Map<Long, CartItemDto> cart) {
        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", cartService.getTotal(cart));
    }
}
