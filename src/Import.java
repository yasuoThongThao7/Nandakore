import java.io.Serializable;

public class Import implements Serializable {
    private String id;
    private Product product;
    private int price_in;
    private double quantity;


    public Import(String product_name, int price_in, String supplier_id, int unit_index, Double quantity) {
        this.id = MakeID.makeIDProduct();
        product = new Product(product_name,supplier_id,unit_index);
        this.price_in = price_in;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getPrice_in() {
        return price_in;
    }

    public void setPrice_in(int price_in) {
        this.price_in = price_in;
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
    public int getUnitIndex(){
        return product.getUnit_k();
    }

    public String getProductName() {
        return product.getProduct_name();
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
    public void setFull(String product_name, int price_in, String supplier_id, int k, Double quantity){
        this.product = new Product(product_name,supplier_id,k);
        this.price_in = price_in;
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }
}
