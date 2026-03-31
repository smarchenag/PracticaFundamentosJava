package Practice5;

import java.util.List;

public interface DiscountStrategy {
    double applyDiscount(double total, List<Product> products);
}
