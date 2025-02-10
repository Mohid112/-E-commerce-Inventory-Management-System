package Dao;

import models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final Connection conn = DatabaseConnection.getConnection();

    // Add a product
    public void addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, category_id, price, quantity, quantity_sold) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                if (conn.isClosed()) {
                    throw new SQLException("❌ Database connection is not established.");
                }

                stmt.setString(1, product.getName());
                stmt.setString(2, product.getDescription());
                stmt.setInt(3, product.getCategoryId());
                stmt.setDouble(4, product.getPrice());
                stmt.setInt(5, product.getQuantity());
                stmt.setInt(6, product.getQuantitySold());

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    conn.commit(); // ✅ Commit transaction
                    System.out.println("✅ Product added successfully!");
                } else {
                    conn.rollback(); // ❌ Rollback if insert fails
                    System.out.println("⚠️ No product was added.");
                }
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                System.err.println("❌ Error inserting product: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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

    // Update product details
    public boolean updateProduct(int id, double newPrice, int newQuantity) {
        String sql = "UPDATE products SET price = ?, quantity = ? WHERE id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, newPrice);
                stmt.setInt(2, newQuantity);
                stmt.setInt(3, id);

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

    // Remove a product by ID
    public boolean removeProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
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

    // Retrieve top-selling products
    public static List<Product> getTopSellingProducts(int limit) {
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
