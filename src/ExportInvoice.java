import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportInvoice {
    private String customerName;
    private String address;
    private List<Export> items;
    private String nameFile;

    public ExportInvoice(String customerName, String address) {
        this.customerName = customerName;
        this.address = address;
        this.items = new ArrayList<>();
        this.nameFile = makeNameFile();
    }

    public String makeNameFile() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String date = LocalDateTime.now().format(formatter);

            return String.format("export_%s.txt",date);
    }

    public void addItem(Export item) {
        items.add(item);
    }

    public List<Export> getItems() {
        return items;
    }

    public void exportToFile() throws IOException {

        File myFile = new File(nameFile);

        if (!myFile.exists()) {
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                throw new IOException("Không thể tạo file: " + nameFile);
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile))) {
            writer.write("===== PHIẾU XUẤT HÀNG =====\n");
            writer.write("Tên khách hàng: " + customerName + "\n");
            writer.write("Địa chỉ: " + address + "\n\n");

            // Tiêu đề bảng
            writer.write("Danh sách hàng hóa:\n");
            writer.write(String.format("%-20s %-10s %-10s %-10s\n", "Tên hàng", "Số lượng", "Đơn giá", "Thành tiền"));
            writer.write("-------------------------------------------------------------\n");

            // Dữ liệu hàng hóa
            for (Export item : items) {
                writer.write(String.format("%-20s %-10.2f %-10s %-10.2f\n",
                        item.getProductName(),
                        item.getQuantity(),
                        String.format("%s/%s",item.getPrice_out(),item.getUnitName()),
                        item.getQuantity() * item.getPrice_out()));
            }

            writer.write("\nTổng cộng: " + String.format("%,.2f VND", calculateTotal()) + "\n");
        } catch (IOException e) {
            throw new IOException("Lỗi khi ghi dữ liệu vào file: " + e.getMessage(), e);
        }

    }

    private double calculateTotal() {
        return items.stream().mapToDouble(item -> item.getQuantity() * item.getPrice_out()).sum();
    }

    public String getFileName() {
        return nameFile;
    }
}
