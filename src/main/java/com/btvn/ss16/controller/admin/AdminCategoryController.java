package com.btvn.ss16.controller.admin;

import com.btvn.ss16.model.Category;
import com.btvn.ss16.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    // ==================== LIST ====================
    @GetMapping({"", "/list"})
    public String list(Model model,
                       @RequestParam(required = false) String keyword) {

        List<Category> categories;

        try {
            if (keyword != null && !keyword.isBlank()) {
                categories = categoryService.getCategoriesByName(keyword);
            } else {
                categories = categoryService.getAllCategories();
            }

            model.addAttribute("categories", categories);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "admin/category";
    }

    // ==================== FORM CREATE ====================
    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/form";
    }

    // ==================== SAVE (CREATE) ====================
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/category/form";
        }

        try {
            categoryService.create(category);
            redirectAttributes.addFlashAttribute("success", "Thêm mới thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/category/list";
    }

    // ==================== EDIT ====================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        try {
            Category category = categoryService.getCategoryById(id);
            model.addAttribute("category", category);
            return "admin/category/form"; // đúng view form
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/category/list";
        }
    }

    // ==================== UPDATE ====================
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("category") Category category,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/category/form";
        }

        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/category/list";
    }

    // ==================== DELETE ====================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         RedirectAttributes redirectAttributes) {

        try {
            Category category = categoryService.getCategoryById(id);

            if (category.getProducts() != null && !category.getProducts().isEmpty()) {
                redirectAttributes.addFlashAttribute("error",
                        "Danh mục vẫn còn sản phẩm, không thể xóa");
                return "redirect:/admin/category/list";
            }

            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Xóa thành công");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/category/list";
    }
}

