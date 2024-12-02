import java.io.Serializable;

public class Supplier implements Serializable {
    private String supplier_id ;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Supplier(String name, String address, String phone, String email) {
        this.supplier_id = MakeID.makeIDSupplier();
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getSupplier_id() {return supplier_id;}

    public void setSupplier_id(String supplier_id) {this.supplier_id = supplier_id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public void setFull(String name, String address, String phone, String email){
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
