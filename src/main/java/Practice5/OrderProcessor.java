package Practice5;

public class OrderProcessor {

    public static Order processOrder(Order order){
        if (order.getCart().getProducts().isEmpty()){
            throw new RuntimeException("El carrito está vacio");
        }
        if (order.getCart().getTotal() <= 0 ){
            throw new RuntimeException("No se puede procesar la orden porque el precio es cero o negativo");
        }
        System.out.println("Recibo de la orden: " + "\n" + order.generateReceipt());
        return order;
    }
}
