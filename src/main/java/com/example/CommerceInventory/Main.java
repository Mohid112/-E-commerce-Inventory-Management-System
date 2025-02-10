package com.example.CommerceInventory;

import models.Product;
import models.Category;
import models.Customer;
import models.Order;
import Services.ProductService;
import Services.CategoryService;
import Services.CustomerService;
import Services.OrderService;
import utils.ReportGenerator;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);
	private static final ProductService productService = new ProductService();
	private static final CategoryService categoryService = new CategoryService();
	private static final CustomerService customerService = new CustomerService();
	private static final OrderService orderService = new OrderService();
	private static final ReportGenerator reportGenerator = new ReportGenerator();

	public static void main(String[] args) {
		while (true) {
			System.out.println("\n📦 E-Commerce Inventory Management System");
			System.out.println("1. Product Management");
			System.out.println("2. Category Management");
			System.out.println("3. Order Management");
			System.out.println("4. Customer Management");
			System.out.println("5. Generate Reports");
			System.out.println("6. Exit");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1: manageProducts(); break;
				case 2: manageCategories(); break;
				case 3: manageOrders(); break;
				case 4: manageCustomers(); break;
				case 5: generateReports(); break;
				case 6: System.out.println("Exiting..."); System.exit(0);
				default: System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}

	// 📦 Product Management Menu
	private static void manageProducts() {
		while (true) {
			System.out.println("\n📦 Product Management");
			System.out.println("1. Add Product");
			System.out.println("2. View All Products");
			System.out.println("3. Search Product");
			System.out.println("4. Update Product");
			System.out.println("5. Delete Product");
			System.out.println("6. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					System.out.print("Enter product name: ");
					String name = scanner.nextLine();
					System.out.print("Enter description: ");
					String description = scanner.nextLine();
					System.out.print("Enter category: ");
					int categoryId = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter price: ");
					double price = scanner.nextDouble();
					System.out.print("Enter quantity: ");
					int quantity = scanner.nextInt();
					System.out.print("Enter quantitySold: ");
					int quantitySold = scanner.nextInt();



					Product newProduct = new Product(0, name, description, categoryId, price, quantity, quantitySold);
					productService.addProduct(newProduct);
					System.out.println("✅ Product added successfully!");
					break;

				case 2:
					List<Product> products = productService.getAllProducts();
					System.out.println("\n📦 Product List:");
					for (Product p : products) {
						System.out.println(p.getId() + " | " + p.getName() + " | " + p.getPrice() + " | " + p.getQuantitySold());
					}
					break;

				case 3:
					System.out.print("Enter product name: ");
					String searchName = scanner.nextLine();
					Product foundProduct = productService.searchProductByName(searchName);
					if (foundProduct != null) {
						System.out.println("✅ Product Found: " + foundProduct.getName() + " - Price: $" + foundProduct.getPrice());
					} else {
						System.out.println("❌ Product not found!");
					}
					break;

				case 4:
					System.out.print("Enter product ID to update: ");
					int productId = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					System.out.print("Enter new price: ");
					double newPrice = scanner.nextDouble();
					System.out.print("Enter new quantity: ");
					int newQuantity = scanner.nextInt();
					productService.updateProduct(productId, newPrice, newQuantity);
					System.out.println("✅ Product updated successfully!");
					break;

				case 5:
					System.out.print("Enter product ID to delete: ");
					int deleteId = scanner.nextInt();
					productService.deleteProduct(deleteId);
					System.out.println("✅ Product deleted successfully!");
					break;

				case 6: return;
				default: System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}

	// 📁 Category Management Menu
	private static void manageCategories() {
		while (true) {
			System.out.println("\n📁 Category Management");
			System.out.println("1. Add Category");
			System.out.println("2. View All Categories");
			System.out.println("3. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					System.out.print("Enter category name: ");
					String categoryName = scanner.nextLine();
					System.out.print("Enter category description: ");
					String categoryDesc = scanner.nextLine();
					Category newCategory = new Category(0, categoryName, categoryDesc);
					categoryService.addCategory(newCategory);
					System.out.println("✅ Category added successfully!");
					break;

				case 2:
					List<Category> categories = categoryService.getAllCategories();
					System.out.println("\n📁 Category List:");
					for (Category c : categories) {
						System.out.println(c.getId() + " | " + c.getName());
					}
					break;

				case 3: return;
				default: System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}


	// 📦 Order Management Menu
	private static void manageOrders() {
		while (true) {
			System.out.println("\n📦 Order Management");
			System.out.println("1. Create Order");
			System.out.println("2. View All Orders");
			System.out.println("3. Update Order Status");
			System.out.println("4. Cancel Order");
			System.out.println("5. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
				case 1:
					System.out.print("Enter customer ID: ");
					int customerId = scanner.nextInt();
					System.out.print("Enter product ID: ");
					int productId = scanner.nextInt();
					System.out.print("Enter quantity: ");
					int quantity = scanner.nextInt();
					scanner.nextLine();

					// Automatically setting order date to the current date
					Date orderDate = new Date(System.currentTimeMillis());

					Order newOrder = new Order(0, customerId, orderDate, productId, quantity, "Pending");
					orderService.addOrder(newOrder);
					System.out.println("✅ Order created successfully!");
					break;

				case 2:
					List<Order> orders = orderService.getAllOrders();
					if (orders.isEmpty()) {
						System.out.println("⚠️ No orders found.");
					} else {
						System.out.println("\n📦 Order List:");
						for (Order o : orders) {
							System.out.println("Order ID: " + o.getId() +
									" | Customer ID: " + o.getCustomerId() +
									" | Product ID: " + o.getProductId() +
									" | Quantity: " + o.getQuantity() +
									" | Date: " + o.getOrderDate() +
									" | Status: " + o.getStatus());
						}
					}
					break;

				case 3:
					System.out.print("Enter order ID: ");
					int orderId = scanner.nextInt();
					scanner.nextLine();
					System.out.print("Enter new status (Pending/Shipped/Delivered): ");
					String newStatus = scanner.nextLine();
					orderService.updateOrderStatus(orderId, newStatus);
					System.out.println("✅ Order status updated!");
					break;



				case 4:
					return;

				default:
					System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}

	// 📋 Customer Management Menu
	private static void manageCustomers() {
		while (true) {
			System.out.println("\n👤 Customer Management");
			System.out.println("1. Add New Customer");
			System.out.println("2. View All Customers");
			System.out.println("3. Search Customer by Name");
			System.out.println("4. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1:
					System.out.print("Enter customer name: ");
					String name = scanner.nextLine();
					System.out.print("Enter customer address: ");
					String address = scanner.nextLine();
					System.out.print("Enter contact number: ");
					String contact = scanner.nextLine();

					Customer newCustomer = new Customer(0, name, address, contact);
					customerService.addCustomer(newCustomer);
					System.out.println("✅ Customer added successfully!");
					break;

				case 2:
					List<Customer> customers = customerService.getAllCustomers();
					System.out.println("\n👤 Customer List:");
					for (Customer c : customers) {
						System.out.println(c.getId() + " | " + c.getName() + " | " + c.getAddress() + " | " );
					}
					break;

				case 3:
					System.out.print("Enter customer name to search: ");
					String searchName = scanner.nextLine();
					Customer foundCustomer = customerService.searchCustomer(searchName);
					if (foundCustomer != null) {
						System.out.println("✅ Customer Found: " + foundCustomer.getName() );
					} else {
						System.out.println("❌ Customer not found!");
					}
					break;

				case 4:
					return; // Go back to the main menu

				default:
					System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}


	// 📊 Report Generation Menu
// 📊 Report Generation Menu
	private static void generateReports() {
		while (true) {
			System.out.println("\n📊 Report Generation");
			System.out.println("1. Top-Selling Products Report");
			System.out.println("2. Products by Category Report");
			System.out.println("3. Orders by Date Range Report");
			System.out.println("4. Back to Main Menu");
			System.out.print("Enter choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
				case 1:
					System.out.print("Enter the number of top-selling products to display: ");
					int limit = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					reportGenerator.getTopSellingProducts(limit);
					break;

				case 2:
					System.out.print("Enter Category ID: ");
					int categoryId = scanner.nextInt();
					scanner.nextLine(); // Consume newline
					reportGenerator.generateProductReportByCategory(categoryId);
					break;

				case 3:
					try {
						System.out.print("Enter start date (YYYY-MM-DD): ");
						String startDateStr = scanner.nextLine();
						System.out.print("Enter end date (YYYY-MM-DD): ");
						String endDateStr = scanner.nextLine();

						// Convert Strings to java.sql.Date
						Date startDate = Date.valueOf(startDateStr);
						Date endDate = Date.valueOf(endDateStr);

						reportGenerator.generateOrderByDateRange(startDate, endDate);
					} catch (IllegalArgumentException e) {
						System.out.println("❌ Invalid date format! Please use YYYY-MM-DD.");
					}
					break;

				case 4:
					return; // Go back to the main menu

				default:
					System.out.println("❌ Invalid choice! Try again.");
			}
		}
	}



}

