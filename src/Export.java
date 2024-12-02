import java.io.Serializable;

public class Export implements Serializable {
    private String id;
    private final Product product;
    private double quantity;
    private int price_out;

    public Export(Import p, double quantity , int price_out ) {
        this.id = MakeID.makeIDProduct();
        this.product = p.getProduct();
        this.quantity = quantity;
        this.price_out = price_out;
    }

    public int getPrice_out() {
        return price_out;
    }

    public void setPrice_out(int price_out) {
        this.price_out = price_out;
    }

    public String getStringQuantity() {
        if(Unit.isIt(product.getUnit_k()))
            return String.format("%.0f", quantity);
        else
            return String.format("%.2f", quantity);
    }


    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public String getID() {
        return id;
    }

    public String getSupplierID() {
        return product.getSupplier_id();
    }

    public String getUnitName() {
        return Unit.unit_name[product.getUnit_k()];
    }

    public String getProductName() {
        return product.getProduct_name();
    }

    public double getQuantity() {
        return quantity;
    }
}