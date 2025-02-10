package Dao;

import models.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Add a category
    public void addCategory(Category category) {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, category.getName());
                stmt.setString(2, category.getDescription());

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    conn.commit(); // ✅ Commit transaction
                    System.out.println("✅ Category added successfully!");
                } else {
                    conn.rollback(); // ❌ Rollback if insert fails
                    System.out.println("⚠️ No category was added.");
                }
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                System.err.println("❌ Error inserting category: " + e.getMessage());
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all categories
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                categoryList.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    // Update a category
    public boolean updateCategory(int id, String newName, String newDescription) {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newName);
                stmt.setString(2, newDescription);
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

    // Remove a category by ID
    public boolean removeCategory(int id) {
        String sql = "DELETE FROM categories WHERE id = ?";

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
}
