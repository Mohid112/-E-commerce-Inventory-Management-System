package Dao;

import models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final Connection conn = DatabaseConnection.getConnection();

    // Add a customer (with transaction handling)
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (name, address, contact) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, customer.getName());
                stmt.setString(2, customer.getAddress());
                stmt.setString(3, customer.getContact());

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    conn.commit(); // ✅ Commit transaction
                    System.out.println("✅ Customer added successfully!");
                    return true;
                } else {
                    conn.rollback(); // ❌ Rollback if insert fails
                    System.out.println("⚠️ No customer was added.");
                    return false;
                }
            } catch (SQLException e) {
                conn.rollback(); // 🔄 Rollback on error
                System.err.println("❌ Error inserting customer: " + e.getMessage());
                return false;
            } finally {
                conn.setAutoCommit(true); // 🔄 Restore AutoCommit
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customers";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    // Get a customer by name
    public Customer getCustomerByName(String name) {
        String sql = "SELECT * FROM customers WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("contact")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if no customer found
    }

    // Update customer details
    public boolean updateCustomer(int id, String newName, String newAddress, String newContact) {
        String sql = "UPDATE customers SET name = ?, address = ?, contact = ? WHERE id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, newName);
                stmt.setString(2, newAddress);
                stmt.setString(3, newContact);
                stmt.setInt(4, id);

                boolean updated = stmt.executeUpdate() > 0;
                if (updated) {
                    conn.commit(); // ✅ Commit update
                    return true;
                } else {
                    conn.rollback(); // ❌ Rollback if update fails
                    return false;
                }
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

    // Remove a customer by ID
    public boolean removeCustomer(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";

        try {
            conn.setAutoCommit(false); // 🚀 Disable AutoCommit

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                boolean deleted = stmt.executeUpdate() > 0;

                if (deleted) {
                    conn.commit(); // ✅ Commit delete
                    return true;
                } else {
                    conn.rollback(); // ❌ Rollback if delete fails
                    return false;
                }
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
