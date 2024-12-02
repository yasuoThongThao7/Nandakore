import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class JPanelWarehouseExport extends JPanelMini {
    private JLabel text = new JLabel("Xuất kho");
    private JLabel image = new JLabel();

    public JPanelWarehouseExport() {
        //image
        image.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/rakho.png"))));
        image.setBounds(20,2,48,48);
        add(image);
        //text
        text.setFont(new Font("Tahoma", Font.BOLD, 15));
        text.setBounds(130, 10, 100, 30);
        add(text);
        // Thiết lập JPanelHome
        setBounds(0, 370, 280, 60);
        setLayout(null);
        setBackground(new Color(25, 188, 211));
    }
}

class WarehouseExportMenu extends JPanel{

    private static WarehouseExportMenu instance = new WarehouseExportMenu();

    private WarehouseExportMenu() {
            initialize();
            productFormPanelAction();
            jTable.getEditButton().addDeleteButtonListener(WareHouseExportFactory.getInstance());
    }

    private void initialize(){
        // Panel chứa JTextField
        panelText = new JPanel();
        panelText.setLayout(null);
        panelText.setBackground(new Color(25, 188, 211));

        //Thiết lập JTable
        if( WareHouseExportFactory.getInstance() != null) {
            loadTableDataMode1();
        }

        // Thiết lập table2
        if(WareHouseFactory.getInstance() != null) {
            loadTableDataMode2();
        }
        jTable = new Table(model1);
        jTable.getEditButton().getPanel().remove(jTable.getEditButton().getEditButton());
        // Thêm thanh cuộn
        jScrollPane = new ScrollPane(jTable);
        jScrollPane.setBounds(25, 50, 750, 303);
        // Tên table
        JLabel label = new JLabel("DANH SÁCH HÀNG XUẤT : ");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(10, 10, 250, 30);
        label.setForeground(new Color(102, 102, 102, 252));
        // Table
        panelTable = new RoundedPanel(30);
        panelTable.setBounds(50,280,800,350);
        panelTable.add(jScrollPane);
        panelTable.add(label);

        // Thiết lập JPanel
        add(productFormPanel);
        add(panelTable);
        setLayout(null);
        setBackground(new Color(239, 239, 239, 50));
        setBounds(280, 0, 920, 680);

    }


// Hàm make row 1
    private Object[] makeRow1(Export e) {
            return new Object[] {e.getID(),
                    e.getProductName(),
                    SupplierFactory.getInstance()
                            .getSuppliersName(e.getSupplierID()),
                    e.getStringQuantity(),
                    String.format("%s/%s", e.getPrice_out(), e.getUnitName())
            };
    }
// Hàm make row 2
    private Object[] makeRow2(Import e) {
        return new Object[] {e.getID(),e.getProductName()};
    }
//Hàm xảy ra khi thêm Đối tượng mới vào WarehouseFactory
    public void addTableData(Import e){
        model2.addRow(makeRow2(e));
    }

//Hàm load data trong table
    private  void loadTableDataMode1() {
        for (Export anExport : WareHouseExportFactory.getInstance().getList()) {
            model1.addRow(makeRow1(anExport));
        }
    }

    private void loadTableDataMode2() {
        for (Import anImport : WareHouseFactory.getInstance().getList()) {
            model2.addRow(makeRow2(anImport));
        }
    }

    // Thay đổi logic cho nút Add
    private void productFormPanelAction() {
        productFormPanel.addButtonActionListener(e -> {
            // Hiển thị form nhập thông tin khách hàng
            OrderInputForm orderForm = new OrderInputForm();
            orderForm.setVisible(true);

            // Xử lý khi nhấn nút Đồng ý trên form
            orderForm.confirmButtonActionPerformed(event -> {
                String name = orderForm.getNameField().getText().trim();
                String address = orderForm.getAddressField().getText().trim();

                // Kiểm tra nếu thông tin khách hàng không hợp lệ
                if (name.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            orderForm,
                            "Vui lòng nhập đầy đủ thông tin!",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Thu thập các hàng được chọn trong bảng
                ArrayList<Export> exportList = new ArrayList<>();
                int rowCount = productFormPanel.getModel().getRowCount();

                for (int i = 0; i < rowCount; i++) {
                    if (isRowSelected(productFormPanel.getModel(), i)) {
                        try {
                            double quality = parseDoubleSafe(productFormPanel.getModel().getValueAt(i, 2));
                            int priceOut = parseIntSafe(productFormPanel.getModel().getValueAt(i, 3));

                            Import anImport = WareHouseFactory.getInstance().getList().get(i);
                            Export anExport = new Export(anImport, quality, priceOut);

                            // Kiểm tra và cập nhật số lượng tồn kho
                            updateInventory(anImport, quality,i);
                            exportList.add(anExport);
                        } catch (TextFieldException | IllegalArgumentException ex) {
                            JOptionPane.showMessageDialog(
                                    productFormPanel,
                                    ex.getMessage(),
                                    "Lỗi",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return; // Nếu có lỗi, không xử lý tiếp
                        }
                    }
                }

                // Nếu không có lỗi, tạo hóa đơn và xử lý danh sách xuất
                if (!exportList.isEmpty()) {
                    ExportInvoice invoice = new ExportInvoice(name, address);
                    exportList.forEach(invoice::addItem);

                    try {
                        // Xuất hóa đơn ra file
                        invoice.exportToFile();
                        JOptionPane.showMessageDialog(
                                productFormPanel,
                                "Hóa đơn đã được xuất ra tệp: " + invoice.getFileName(),
                                "Thành công",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        // Thêm các đối tượng vào WarehouseExportFactory
                        for (Export export : exportList) {
                            WareHouseExportFactory.getInstance().add(export);
                            model1.addRow(makeRow1(export));
                        }

                        // Xóa các hàng đã xử lý khỏi bảng
                        for (int i = rowCount - 1; i >= 0; i--) {
                            if (isRowSelected(productFormPanel.getModel(), i)) {
                                productFormPanel.getModel().removeRow(i);
                            }
                        }

                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(
                                productFormPanel,
                                "Không thể xuất hóa đơn ra file!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

                // Đóng form thông tin khách hàng
                orderForm.dispose();
            });
        });
    }



    private boolean isRowSelected(DefaultTableModel model, int rowIndex) {
        Object value = model.getValueAt(rowIndex, 4);
        return value instanceof Boolean && (Boolean) value;
    }


//Hàm bắt ngoại lệ số lượng
    private double parseDoubleSafe(Object value) {
        try {
            return Double.parseDouble(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số lượng không hợp lệ!");
        }
    }
//Hàm bắt ngoại lệ Giá xuất
    private int parseIntSafe(Object value) {
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Giá xuất không hợp lệ!");
        }
    }
//Hàm bắt ngoại lệ số lượng
    private void updateInventory(Import anImport, double quality,int row) throws TextFieldException {
        double remainingQuantity = anImport.getQuantity() - quality;
        if (remainingQuantity < 0) {
            throw new TextFieldException("Số lượng trong kho không đủ!");
        }

        if (remainingQuantity == 0) {
            WareHouseFactory.getInstance().remove(anImport);
            WarehouseMenu.getInstance().getModel1().removeRow(row);
        } else {
            anImport.setQuantity(remainingQuantity);
            WareHouseFactory.getInstance().setFile();
            WarehouseMenu.getInstance().getModel1().setValueAt(remainingQuantity,row,3);
        }
    }



    public static WarehouseExportMenu getInstance() {
        return instance;
    }
//**********************************************************************************************************************
    private Table jTable;
    private JScrollPane jScrollPane;
    private JPanel panelTable,panelText;
    private String[] columnNames = {"ID","Mặt hàng","Hãng","Số lượng","Hoàn thành"};
    private String[] data = {"ID","SP","Số lượng","Giá xuất","Thêm"};
    private DefaultTableModel model1 = new DefaultTableModel(columnNames,0){
        @Override
        public boolean isCellEditable(int row, int column) {
            if (column != this.getColumnCount() - 1 )
                return false;
            return true;
        }
    };
    private DefaultTableModel model2 = new DefaultTableModel(data,0){
        @Override
        public boolean isCellEditable(int row, int column) {
            return column > 1;
        }
    };
    private ProductFormPanel productFormPanel= new ProductFormPanel(model2);

}
