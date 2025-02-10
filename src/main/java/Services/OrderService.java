package Services;

import models.Order;
import Dao.OrderDAO;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;

    // Constructor to initialize OrderDAO
    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    // Add a new order (Save to Database)
    public void addOrder(Order order) {
        orderDAO.addOrder(order);
        System.out.println("✅ Order placed successfully! Order ID: " + order.getId());
    }

    // Retrieve all orders from the database
    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public boolean updateOrderStatus(int id, String newStatus) {
        return orderDAO.updateOrderStatus(id, newStatus);
    }
}
