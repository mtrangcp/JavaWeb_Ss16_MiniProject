package com.btvn.ss16.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    @PersistenceContext
    private EntityManager entityManager;

    public Double getTotalRevenue() {

        String hql = "SELECT SUM(o.totalAmount) FROM Order o";
        Query query = entityManager.createQuery(hql);
        Double total = (Double) query.getSingleResult();
        return total != null ? total : 0.0;
    }

    public List<Map<String, Object>> getTop5SellingProducts() {

        String hql = "SELECT p.name, SUM(od.quantity), SUM(od.unitPrice * od.quantity) " +
                "FROM OrderDetail od JOIN od.order o JOIN od.product p " +
                "GROUP BY p.id, p.name " +
                "ORDER BY SUM(od.quantity) DESC";

        Query query = entityManager.createQuery(hql);
        query.setMaxResults(5);

        List<Object[]> results = query.getResultList();
        List<Map<String, Object>> topProducts = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", row[0]);
            map.put("totalSold", row[1]);
            map.put("totalRevenue", row[2]);
            topProducts.add(map);
        }

        return topProducts;
    }
}