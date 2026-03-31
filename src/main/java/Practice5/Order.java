package Practice5;

public class Order {
    private String customer;
    private Cart cart;
    private final DiscountStrategy discountStrategy;

    public Order(DiscountStrategy discountStrategy, Cart cart, String customer) {
        this.discountStrategy = discountStrategy;
        this.cart = cart;
        this.customer = customer;
    }

    double getFinalPrice() {
        return discountStrategy.applyDiscount(cart.getTotal(), cart.getProducts());
    }

    public String generateReceipt() {

        double subTotal = cart.getTotal();
        double finalPrice = getFinalPrice();
        double discount = subTotal - finalPrice;

        return String.format("Nombre del cliente: %s " + "\n" +
                "Productos: %s" + "\n"
                 + "Subtotal: $%.2f" + "\n"
                + "Descuento: $%.2f" + "\n"
                + "Total: $%.2f" + "\n", customer, cart.getProducts(),cart.getTotal(), discount,this.getFinalPrice());
    }


    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }
}
