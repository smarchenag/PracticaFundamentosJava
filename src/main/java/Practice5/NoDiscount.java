package Practice5;

import java.util.List;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double total, List<Product> products) {
        return total;
    }
}
