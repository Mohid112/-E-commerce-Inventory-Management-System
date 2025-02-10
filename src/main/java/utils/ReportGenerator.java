package utils;

import Dao.ReportDAO;
import models.Product;
import models.Order;

import java.sql.Date;
import java.util.List;

public class ReportGenerator {
    private final ReportDAO reportDAO;

    public ReportGenerator() {
        this.reportDAO = new ReportDAO();
    }

    // ✅ Print all products in a given category
    public void generateProductReportByCategory(int categoryId) {
        List<Product> products = reportDAO.getProductsByCategory(categoryId);
        System.out.println("\n===== PRODUCTS IN CATEGORY " + categoryId + " =====");
        if (products.isEmpty()) {
            System.out.println("⚠️ No products found in this category.");
        } else {
            for (Product product : products) {
                System.out.println("ID: " + product.getId() +
                        ", Name: " + product.getName() +
                        ", Price: $" + product.getPrice() +
                        ", Stock: " + product.getQuantity());
            }
        }
    }

    // ✅ Print all orders within a date range (Fixed Date Format)
    public void generateOrderByDateRange(Date startDate, Date endDate) {
        List<Order> orders = reportDAO.getOrdersByDateRange(startDate, endDate);
        System.out.println("\n===== ORDERS FROM " + startDate + " TO " + endDate + " =====");
        if (orders.isEmpty()) {
            System.out.println("⚠️ No orders found in this date range.");
        } else {
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getId() +
                        ", Customer ID: " + order.getCustomerId() +
                        ", Product ID: " + order.getProductId() +
                        ", Quantity: " + order.getQuantity() +
                        ", Date: " + order.getOrderDate() +
                        ", Status: " + order.getStatus());
            }
        }
    }

    // ✅ Print top-selling products
    public void getTopSellingProducts(int limit) {
        List<Product> topProducts = reportDAO.getTopSellingProducts(limit);
        System.out.println("\n===== TOP " + limit + " BEST-SELLING PRODUCTS =====");
        if (topProducts.isEmpty()) {
            System.out.println("⚠️ No sales data available.");
        } else {
            for (Product product : topProducts) {
                System.out.println("ID: " + product.getId() +
                        ", Name: " + product.getName() +
                        ", Sales: " + product.getQuantitySold());
            }
        }
    }
}
