package Dao;

import models.Product;
import models.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // ✅ Get all products in a given category
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("quantity_sold")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // ✅ Get all orders within a specific date range
    public List<Order> getOrdersByDateRange(Date startDate, Date endDate) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE order_date BETWEEN ? AND ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                orderList.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    // ✅ Get top-selling products
    public List<Product> getTopSellingProducts(int limit) {
        List<Product> topProducts = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY quantity_sold DESC LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topProducts.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getInt("quantity_sold")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topProducts;
    }
}
