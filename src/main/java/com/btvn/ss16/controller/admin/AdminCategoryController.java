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

    @GetMapping({"", "/list"})
    public String list(Model model, @RequestParam(required = false) String keyword) {
        try {
            List<Category> categories = (keyword != null && !keyword.isBlank())
                    ? categoryService.getCategoriesByName(keyword)
                    : categoryService.getAllCategories();

            model.addAttribute("categories", categories);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "admin/category-list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("category") Category category,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "admin/category-form";

        try {
            categoryService.create(category);
            redirectAttributes.addFlashAttribute("success", "Thêm mới thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/category/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("category", categoryService.getCategoryById(id));
            return "admin/category-form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/category/list";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("category") Category category,
                         BindingResult result,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) return "admin/category-form";

        try {
            categoryService.update(category);
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/category/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category.getProducts() != null && !category.getProducts().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Danh mục còn sản phẩm, không thể xóa");
            } else {
                categoryService.delete(id);
                redirectAttributes.addFlashAttribute("success", "Xóa thành công");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/category/list";
    }
}