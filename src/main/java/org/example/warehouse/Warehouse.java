package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {

    private final String name;
    private final List<ProductRecord> products;
    private final Map<UUID, ProductRecord> productMap;
    private final Set<UUID> changedProductIds;

    private Warehouse() {
        this.name = "MyStore";
        this.products = new ArrayList<>();
        this.productMap = new HashMap<>();
        this.changedProductIds = new HashSet<>();
    }

    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.productMap = new HashMap<>();
        this.changedProductIds = new HashSet<>();
    }

    public static Warehouse getInstance() {
       return new Warehouse();

    }

    public static Warehouse getInstance(String name) {
    return new Warehouse(name);
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Product name can't be null or empty.");
        }

        if (category == null) {
            throw new IllegalArgumentException("Category can't be null.");
        }

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (price == null) {
            price = BigDecimal.ZERO;
        }

        if (productMap.containsKey(id)) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }

        ProductRecord product = new ProductRecord(id, name, category, price);
        products.add(product);
        productMap.put(id, product);

        return product;
    }

    public void updateProductPrice(UUID productId, BigDecimal newPrice) {
        if (!productMap.containsKey(productId)) {
            throw new IllegalArgumentException("Product with that id doesn't exist.");
        }

        ProductRecord product = productMap.get(productId);
        product.setPrice(newPrice);
        changedProductIds.add(productId);
    }

    public List<ProductRecord> getChangedProducts() {
        return products.stream()
                .filter(product -> changedProductIds.contains(product.uuid()))
                .collect(Collectors.toList());
    }

    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    public Optional<ProductRecord> getProductById(UUID productId) {
        return Optional.ofNullable(productMap.get(productId));
    }

    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }
}