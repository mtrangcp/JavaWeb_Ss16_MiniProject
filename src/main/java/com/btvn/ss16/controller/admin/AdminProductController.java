package com.btvn.ss16.controller.admin;

import com.btvn.ss16.dto.ProductSearchDto;
import com.btvn.ss16.model.Product;
import com.btvn.ss16.repository.CategoryRepository;
import com.btvn.ss16.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    // danh sách phân trang
    @GetMapping
    public String list(@ModelAttribute ProductSearchDto searchDto, Model model) {

        Page<Product> page = productService.search(
                searchDto.getName(),
                searchDto.getMinPrice(),
                searchDto.getMaxPrice(),
                searchDto.getCategoryId(),
                searchDto.getPage(),
                searchDto.getSize()
        );

        model.addAttribute("products", page);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("searchDto", searchDto);

        return "admin/product-list";
    }

    // form tạo mới
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product-form";
    }

    // lưu
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            return "admin/product-form";
        }

        productService.save(product, file);

        return "redirect:/admin/product";
    }

    // chỉnh sửa theo id
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        Product product = productService.findById(id);

        if (product == null) {
            return "redirect:/admin/product";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());

        return "admin/product-form";
    }

    // xoá theo id
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        productService.delete(id);

        return "redirect:/admin/product";
    }
}
