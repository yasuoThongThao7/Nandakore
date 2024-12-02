import java.io.*;
import java.util.ArrayList;

public class WareHouseExportFactory implements InventoryManager<Export> {
    private static final String FILE_NAME = "warehouseExport.ser";
    private static final WareHouseExportFactory instance = new WareHouseExportFactory();
    private ArrayList<Export> list = new ArrayList<>();

    private WareHouseExportFactory() {
        loadFromFile();
    }

    public static WareHouseExportFactory getInstance() {
        return instance;
    }

    private void loadFromFile() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             ObjectInputStream obIn = new ObjectInputStream(fileIn))
        {
            while (true) {
                try {
                    Export order = (Export) obIn.readObject();
                    list.add(order);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file, bắt đầu với một danh sách trống");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khởi tạo dữ liệu từ file.", e);
        }
    }

    @Override
    public void add(Export o) {
        list.add(o);
        setFile();
    }

    @Override
    public void remove(Export o) {
        list.remove(o);
        setFile();
    }

    @Override
    public void set(int index, Export item) {
        list.set(index, item);
        setFile();
    }



    public void setFile() {
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream obOut = new ObjectOutputStream(fileOut))
        {
            for (Export o : list) {
                obOut.writeObject(o);
            }
            System.out.println("Lưu/Xóa dữ liệu thành công.");
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi/xóa file: ", e);
        }
    }

    public ArrayList<Export> getList() {
        return list;
    }
}
