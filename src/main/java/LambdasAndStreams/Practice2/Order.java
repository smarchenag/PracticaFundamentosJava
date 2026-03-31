package LambdasAndStreams.Practice2;

public class Order {
    private String id;
    private String customer;
    private String product;
    private int quantity;
    private double unitPrice;
    private String status;
    private double total;

    public Order(String id, String customer, String product, int quantity, double unitPrice, String status) {
        this.id = id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getStatus() {
        return status;
    }

    public double getTotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", customer='" + customer + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", status='" + status + '\'' +
                ", total='" + getTotal() + '\'' +
                '}';
    }
}
