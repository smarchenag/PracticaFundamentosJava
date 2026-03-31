package CollectionsAndGenerics.Practice5;

import java.util.*;

public class Inventory {
    private List<Product> products;

    public Inventory(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(String name) {
        boolean removed = products.removeIf(p -> p.getName().equalsIgnoreCase(name));
        if (!removed) {
            throw new IllegalArgumentException("Product not found");
        }
    }

    public void updateStock(String name, int quantity) {
        Product product = products.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst().orElseThrow( () -> new IllegalArgumentException("Product not found"));
        product.setQuantity(quantity);
        if (product.getQuantity() <= 0) {
            product.setInStock(false);
        }
    }

    public List<Product> findByCategory(String category) {
        return products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).toList();
    }

    public List<Product> findByPriceRange (double min, double max) {
        return products.stream().filter(p -> p.getPrice() >= min && p.getPrice() <= max).toList();
    }

    public Optional<Product> findCheapest() {
        return products.stream().filter(Product::isInStock).min(Comparator.comparing(Product::getPrice));
    }

    public Map<String, List<Product>> groupByCategory() {
        Map<String, List<Product>> map = new HashMap<>();
        for (Product product : products) {
            map.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        }
        return map;
    }

    public Map<String, Double> averagePriceByCategory() {
        Map<String, Double> map = new HashMap<>();
        for (Map.Entry<String, List<Product>> entry : groupByCategory().entrySet()) {
            map.put(entry.getKey(), entry.getValue().stream().mapToDouble(Product::getPrice).average().orElse(0.0));
        }
        return map;
        //Otra opcion
        //return products.stream().collect(Collectors.groupingBy(Product::getCategory));
    }

    public List<Product> getLowStock(int threshold) {
        return products.stream().filter(p -> p.getQuantity() <= threshold && p.isInStock()).toList();
    }

    public double getTotalValue() {
        return  products.stream().mapToDouble(p -> p.getPrice() * p.getQuantity()).sum();
    }

    public List<Product> getSortedBy(Comparator<Product> comparator) {
        return products.stream().sorted(comparator).toList();
    }

    public List<Product> getProducts() {
        return products;
    }
}
