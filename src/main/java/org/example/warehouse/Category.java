package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private static final Map<String, Category> categoryMap = new HashMap<>();

    private final String name;

    private Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        String categoryName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        if (!categoryMap.containsKey(categoryName)) {
            categoryMap.put(categoryName, new Category(categoryName));
        }

        return categoryMap.get(categoryName);
    }

    public String getName() {
        return name;
    }
}