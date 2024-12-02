import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String FILE_NAME = "admin.ser";

    private final String username;
    private String password;
    private static Admin instance;


    private Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static synchronized Admin getInstance() {
        if (instance == null) {
            instance = loadFromFile();
            if (instance == null) {
                instance = new Admin("admin", "123456789");
                saveToFile(instance);
            }
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        saveToFile(this);
    }

    // Ghi đối tượng Admin vào file
    private static void saveToFile(Admin admin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(admin);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi ghi file admin: " + e.getMessage(), e);
        }
    }

    // Đọc đối tượng Admin từ file
    private static Admin loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (Admin) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File admin chưa tồn tại.");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khi đọc file admin: " + e.getMessage(), e);
        }
    }
}

public class AdminService {
    private static AdminService instance;

    // Private constructor để ngăn việc tạo mới
    private AdminService() {
    }

    // Singleton cho AdminService
    public static synchronized AdminService getInstance() {
        if (instance == null) {
            instance = new AdminService();
        }
        return instance;
    }

    // Kiểm tra thông tin đăng nhập
    public boolean check(String username, String password) {
        Admin admin = Admin.getInstance();
        return admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }
    public boolean check( String password) {
        Admin admin = Admin.getInstance();
        return admin.getPassword().equals(password);
    }

    // Cập nhật password
    public void setPassword(String password) {
        Admin admin = Admin.getInstance();
        admin.setPassword(password);
    }
}

class MakeID{

    public static String makeIDSupplier() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String date = LocalDateTime.now().format(formatter);

        return String.format("SUP%s",date);
    }

    public static String makeIDProduct() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String date = LocalDateTime.now().format(formatter);

        return String.format("P%s",date);
    }

}
