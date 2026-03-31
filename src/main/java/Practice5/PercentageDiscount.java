package Practice5;

import java.util.List;

public class PercentageDiscount implements DiscountStrategy {

    private final double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double total, List<Product> products    ) {
        return total - (total * percentage / 100);
    }
}
