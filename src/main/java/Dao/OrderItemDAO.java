package Dao;

import models.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Add items to an order (with transaction handling)
    public void addOrderItem(int orderId, int productId, int quantity) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);
                stmt.setInt(3, quantity);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    conn.commit(); // ✅ Commit transaction
                    System.out.println("✅ Product added to order successfully!");
                } else {
                    conn.rollback(); // ❌ Rollback if insert fails
                    System.out.println("⚠️ No product was added to the order.");
                }
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                System.err.println("❌ Error adding product to order: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all products in an order
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                orderItems.add(new OrderItem(
                        rs.getInt("order_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    // Update an order item
    public boolean updateOrderItem(int orderId, int productId, int newQuantity) {
        String sql = "UPDATE order_items SET quantity = ? WHERE order_id = ? AND product_id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newQuantity);
                stmt.setInt(2, orderId);
                stmt.setInt(3, productId);

                boolean updated = stmt.executeUpdate() > 0;
                if (updated) {
                    conn.commit(); // ✅ Commit update
                } else {
                    conn.rollback(); // ❌ Rollback if update fails
                }

                return updated;
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove an item from an order
    public boolean removeOrderItem(int orderId, int productId) {
        String sql = "DELETE FROM order_items WHERE order_id = ? AND product_id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, orderId);
                stmt.setInt(2, productId);
                boolean deleted = stmt.executeUpdate() > 0;

                if (deleted) {
                    conn.commit(); // ✅ Commit delete
                } else {
                    conn.rollback(); // ❌ Rollback if delete fails
                }

                return deleted;
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
