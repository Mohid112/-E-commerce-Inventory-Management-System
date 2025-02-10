package Services;

import models.Customer;
import Dao.CustomerDAO;
import java.util.List;

public class CustomerService {
    private final CustomerDAO customerDAO;

    // Constructor to initialize CustomerDAO
    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    // Add a new customer (Now Saves to Database)
    public void addCustomer(Customer customer) {
        boolean success = customerDAO.addCustomer(customer);
        if (success) {
            System.out.println("✅ Customer added successfully: " + customer.getName());
        } else {
            System.out.println("❌ Failed to add customer.");
        }
    }

    // Get all customers from the database
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    // Search for a customer by name (Now Searches in DB)
    public Customer searchCustomer(String name) {
        return customerDAO.getCustomerByName(name);
    }


}
