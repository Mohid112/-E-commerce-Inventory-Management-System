package Services;

import models.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private List<Category> categoryList = new ArrayList<>();

    // Add a new category
    public void addCategory(Category category) {
        categoryList.add(category);
        System.out.println("Category added: " + category.getName());
    }

    // View all categories
    public List<Category> getAllCategories() {
        return categoryList;
    }

    // Get category by ID
    public Category getCategoryById(int id) {
        for (Category category : categoryList) {
            if (category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}

