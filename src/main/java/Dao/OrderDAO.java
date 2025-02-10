package Dao;

import models.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private final Connection conn = DatabaseConnection.getConnection();


    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, order_date, product_id, quantity, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setDate(2, order.getOrderDate());
            stmt.setInt(3, order.getProductId());
            stmt.setInt(4, order.getQuantity());
            stmt.setString(5, order.getStatus());
            stmt.executeUpdate();
            System.out.println("✅ Order placed successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Retrieve all orders (includes orderDate)
    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int customerId = rs.getInt("customer_id");
                Date orderDate = rs.getDate("order_date"); // ✅ Retrieving orderDate
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                String status = rs.getString("status");

                orderList.add(new Order(id, customerId, orderDate, productId, quantity, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    // ✅ Retrieve orders within a specific date range
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

    // ✅ Update an order's status
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
            System.out.println("✅ Order status updated to " + status);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Cancel an order
    public void cancelOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            System.out.println("✅ Order cancelled successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
