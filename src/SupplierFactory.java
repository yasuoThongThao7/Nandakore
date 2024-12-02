import java.io.*;
import java.util.ArrayList;

public class SupplierFactory implements InventoryManager<Supplier> {
    private final static String FILE_NAME = "supplier.ser";
    private final ArrayList<Supplier> listSuppliers = new ArrayList<>();
    private final ArrayList<String> listNames = new ArrayList<>();
    private static final SupplierFactory instance = new SupplierFactory();

    private SupplierFactory(){
        loadFromFile();
    }

    private void loadFromFile(){
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream obIn = new ObjectInputStream(fileIn))
        {
            while (true) {
                try {
                    Supplier supplier = (Supplier) obIn.readObject();
                    listSuppliers.add(supplier);
                    listNames.add(supplier.getName());
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File lỗi.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khởi tạo dữ liệu từ tập tin.");
        }
    }

    @Override
    public void add(Supplier supplier) {
        listSuppliers.add(supplier);
        listNames.add(supplier.getName());
        setFile();
    }
    @Override
    public void remove(Supplier supplier) {
        listSuppliers.remove(supplier);
        listNames.remove(supplier.getName());
        setFile();
    }


    public boolean isContains(String s) {
        for (String name : listNames) {
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String search(String name) {
        for (Supplier supplier : listSuppliers) {
            if (supplier.getName().equals(name)) {
                return supplier.getSupplier_id();
            }
        }
        return null;
    }

    public String getSuppliersName(String ID) {
        for (Supplier supplier : listSuppliers) {
            if (supplier.getSupplier_id().equals(ID)) {
                return supplier.getName();
            }
        }
        return null;
    }

    public void setFile(){
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream obOut = new ObjectOutputStream(fileOut))
        {
            for (Supplier sup : listSuppliers) {
                obOut.writeObject(sup);
                System.out.println("Lưu/Xóa dữ liệu thành công.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi/xóa file: ", e);
        }
    }

    public void set(int index , Supplier supplier) {
        listSuppliers.set(index, supplier);
        listNames.set(index, supplier.getName());
        setFile();
    }


    public static SupplierFactory getInstance(){
        return instance;
    }

    public ArrayList<String> getListNames() {
        return listNames;
    }
    @Override
    public ArrayList<Supplier> getList() {
        return listSuppliers;
    }
}
