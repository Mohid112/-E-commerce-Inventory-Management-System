package Services;

import models.Product;
import Dao.ProductDAO;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    // Constructor to initialize ProductDAO
    public ProductService() {
        this.productDAO = new ProductDAO();
    }

    // Add a new product (Now Saves to Database)
    public void addProduct(Product product) {
        productDAO.addProduct(product);
    }

    // View all products from the database
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    // Search product by name from the database
    public Product searchProductByName(String name) {
        return productDAO.getAllProducts()
                .stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Update product details in the database
    public boolean updateProduct(int id, double newPrice, int newQuantity) {
        return productDAO.updateProduct(id, newPrice, newQuantity);
    }

    // Remove product by ID from the database
    public boolean deleteProduct(int id) {
        return productDAO.removeProduct(id);
    }

    // Get top-selling products from the database
    public List<Product> getTopSellingProducts(int limit) {
        return productDAO.getTopSellingProducts(limit);
    }
}
