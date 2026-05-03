package com.btvn.ss16.controller.admin;


import com.btvn.ss16.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("totalRevenue", statisticsService.getTotalRevenue());
        model.addAttribute("topProducts", statisticsService.getTop5SellingProducts());
        return "admin/dashboard";
    }
}