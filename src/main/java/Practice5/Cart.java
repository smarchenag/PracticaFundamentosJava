package Practice5;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final List<Product> products;

    public Cart(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotal() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear(){
        products.clear();
    }
}
