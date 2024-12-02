import java.io.*;

public class Product implements Serializable {
    private String product_name;
    private String supplier_id;
    private int unit_k;

    public Product( String product_name, String supplier_id,int k) {
        this.product_name = product_name;
        this.supplier_id = supplier_id ;
        this.unit_k = k;
    }


    public String getProduct_name() {
        return product_name;
    }


    public String getSupplier_id() {
        return supplier_id;
    }

    public int getUnit_k() {
        return unit_k;
    }

    public void setUnit_k(int unit_k) {
        this.unit_k = unit_k;
    }
}


class Unit{
    public static String[] unit_name = {"Tấn","Thùng","Kg","Chiếc","m"};

    public static boolean isIt(int k){
        if(k % 2 != 0)
            return true;
        return false;
    }

    public static int index(String unit){
        for(int i = 0; i < 5; i++){
            if(unit_name[i].equals(unit)){
                return i;
            }
        }
        return 0;
    }
}