package Practice5;

import java.util.Comparator;
import java.util.List;

public class BuyNGetFreeDiscount implements DiscountStrategy{

    private final int nCantidad;

    public BuyNGetFreeDiscount(int nCantidad) {
        this.nCantidad = nCantidad;
    }

    public int getnCantidad() {
        return nCantidad;
    }

    @Override
    public double applyDiscount(double total, List<Product> products) {
        double desc = 0;
        if(products.size() >= nCantidad){
            desc = products.stream().min(Comparator.comparing(Product::getPrice)).orElseThrow(() -> new RuntimeException("No hay producto minimo")).getPrice();
        }
        return total - desc;
    }
}
