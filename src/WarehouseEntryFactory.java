import java.io.*;
import java.util.ArrayList;

public class WarehouseEntryFactory implements InventoryManager<Import> {

    private static final String FILE_NAME = "PurchaseOrders.ser";
    private final ArrayList<Import> list = new ArrayList<>();
    private static final WarehouseEntryFactory instance = new WarehouseEntryFactory();

    private WarehouseEntryFactory() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream objectInput = new ObjectInputStream(fileIn)) {
            while (true) {
                try {
                    Import anImport = (Import) objectInput.readObject();
                    list.add(anImport);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found, starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Lỗi khởi tạo dữ liệu từ tập tin.");
        }
    }
    @Override
    public void add(Import anImport) {
        list.add(anImport);
        setFile();
    }

    @Override
    public void remove(Import item) {
        list.remove(item);
        setFile();
    }

    public void set(int index , Import item) {
        list.get(index);
        setFile();
    }

    public void setFile(){
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOut)) {
            for (Import i : list) {
                objectOutput.writeObject(i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi ghi file .", e);
        }
    }
    public Import get(Import i) {
        for (Import o : list) {
            if (o.equals(i)) {
                return o;
            }
        }
        return null;
    }

    public ArrayList<Import> getList() {
        return list;
    }

    public static WarehouseEntryFactory getInstance() {
        return instance;
    }
}
