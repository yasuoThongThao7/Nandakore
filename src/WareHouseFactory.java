import java.io.*;
import java.util.ArrayList;

public class WareHouseFactory implements InventoryManager<Import> {
    public static ArrayList<Import> list = new ArrayList<>();
    private static final String FILE_NAME = "warehouse.ser";
    private static WareHouseFactory instance = new WareHouseFactory();

    private WareHouseFactory() {
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
            System.out.println("Error initializing data from file.");
        }
    }
    @Override
    public void add(Import s) {
        list.add(s);
        setFile();
    }

    @Override
    public void remove(Import s) {
        list.remove(s);
        setFile();
    }

    @Override
    public void set(int index, Import item) {
        list.set(index,item);
        setFile();
    }

    public void setFile(){
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
             ObjectOutputStream objectOutput = new ObjectOutputStream(fileOut)) {
            for (Import s : list) {
                objectOutput.writeObject(s);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file.", e);
        }
    }

    public ArrayList<Import> getList() {
        return list;
    }

    public static WareHouseFactory getInstance() {
        return instance;
    }
}
