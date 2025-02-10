package models;

import java.sql.Date; // Ensure you import java.sql.Date for database compatibility

public class Order {
    private int id;
    private int customerId;
    private Date orderDate;
    private int productId;
    private int quantity;
    private String status;

    // ✅ Corrected Constructor
    public Order(int id, int customerId, Date orderDate, int productId, int quantity, String status) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }



    // ✅ Getters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public Date getOrderDate() { return orderDate; } // Added getter for orderDate
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }

    // ✅ Setters
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; } // Added setter for orderDate
    public void setStatus(String status) { this.status = status; }
}
