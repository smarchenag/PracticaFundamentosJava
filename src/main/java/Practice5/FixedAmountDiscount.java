package Practice5;

import java.util.List;

public class FixedAmountDiscount implements DiscountStrategy{
    private double fixedAmount;

    public FixedAmountDiscount(double fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    @Override
    public double applyDiscount(double total, List<Product> products) {
        return total-fixedAmount;
    }
}
