package Practice5;

import java.util.List;

public class CouponDiscount implements DiscountStrategy{

    private String coupon;

    public CouponDiscount(String coupon) {
        this.coupon = coupon;
    }

    @Override
    public double applyDiscount(double total, List<Product> products) {
        double discount = 0.0;
        if (!coupon.isEmpty()){
            String d = coupon.substring(4);
            //Ejemplo: SALE30 = 30.000 de descuento
            discount = Double.parseDouble(d) * 1000;
        }
        return total-discount;
    }
}
