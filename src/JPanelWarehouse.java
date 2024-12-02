import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class JPanelWarehouse extends JPanelMini{
    private JLabel text = new JLabel("Tồn kho");
    private JLabel image = new JLabel();

    public JPanelWarehouse () {
        //image
        image.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/kho.png"))));
        image.setBounds(20,2,48,48);
        add(image);
        //text
        text.setFont(new Font("Tahoma", Font.BOLD, 15));
        text.setBounds(130, 10, 100, 30);
        add(text);
        // Thiết lập JPanelHome
        setBounds(0, 430, 280, 60);
        setLayout(null);
        setBackground(new Color(25, 188, 211));
    }
}

class WarehouseMenu extends JPanel {

    private static WarehouseMenu instance = new WarehouseMenu();


    private WarehouseMenu() {
        initialize();
        productFormPanelAction();
    }


    private void initialize(){
        panelText = new JPanel();
        panelText.setLayout(null);
        panelText.setBackground(new Color(25, 188, 211));

        //Thiết lập table1
        if( WareHouseFactory.getInstance() != null) {
            loadTableData(model1,WareHouseFactory.getInstance(),true);
        }
        jTable.setShowEditButton(false);
        // Thiết lập table2
        if(WarehouseEntryFactory.getInstance() != null) {
            loadTableData(model2,WarehouseEntryFactory.getInstance(),false);
        }

        // Thêm thanh cuộn
        jScrollPane.setBounds(25, 50, 750, 303);
        // Tên table
        JLabel label = new JLabel("DANH SÁCH HÀNG : ");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(10, 10, 200, 30);
        label.setForeground(new Color(102, 102, 102, 252));
        // Table
        panelTable = new RoundedPanel(30);
        panelTable.setBounds(50,280,800,350);
        panelTable.add(jScrollPane);
        panelTable.add(label);
        //Thiết lập JPanel
        add(productFormPanel);
        add(panelTable);
        setLayout(null);
        setBackground(new Color(239, 239, 239, 50));
        setBounds(280, 0, 920, 680);
    }

    private Object[] makeRow(Import p, boolean isDetailed) {
        if (isDetailed) {
            return new Object[] {p.getID(),
                                 p.getProductName(),
                                 SupplierFactory.getInstance()
                                                .getSuppliersName(p.getSupplierID()),
                                 p.getStringQuantity(),
                                 String.format("%s/%s",p.getPrice_in(),p.getUnitName())
            };
        }
        return new Object[] {p.getID(), p.getProductName()};
    }
//Hàm xảy ra khi thêm Đối tượng mới vào WarehouseEntryFactory
    public void addTableData(Import i){
            model2.addRow(makeRow(i,false));
    }

//Hàm load data trong table
    private  void loadTableData(DefaultTableModel model, InventoryManager<Import> manager, boolean isDetailed) {
        for (Import anImport : manager.getList()) {
            model.addRow(makeRow(anImport, isDetailed));
        }
    }

// Hàm load nếu chỉnh sửa
    public void setTableData(int row,String name){
        model2.setValueAt(name,row,0);
    }

//Hàm sự kiện nút add
    private void productFormPanelAction() {
        productFormPanel.addButtonActionListener(e -> {
            int rowCount = productFormPanel.getModel().getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if (productFormPanel.getModel().getValueAt(i, 2) != null
                     && (Boolean) productFormPanel.getModel().getValueAt(i, 2)) {
                    // Xử lý logic

                    Import order = WarehouseEntryFactory.getInstance().getList().get(i);

                    // Thêm vào warehouse
                    if (WareHouseFactory.getInstance() != null) {
                        WareHouseFactory.getInstance().add(order);
                        WarehouseExportMenu.getInstance().addTableData(order);
                    }

                    // Xóa khỏi danh sách nhập kho
                    WarehouseEntryFactory.getInstance().remove(order);

                    WarehouseEntryMenu.getInstance().getModel().removeRow(i);
                    // Cập nhật model và bảng
                    model1.addRow(makeRow(order,true));
                    productFormPanel.getModel().removeRow(i);
                    i--;
                    rowCount--;
                }
            }
        });
    }


// Phương thức lấy instance
    public static WarehouseMenu getInstance() {
        return instance;
    }

    public DefaultTableModel getModel1() {
        return model1;
    }

    public DefaultTableModel getModel2() {
        return model2;
    }

    //*******************************************************************************************
    private JPanel panelTable,panelText;
    private final String[] COLUMN_NAMES_1 = {"ID","Mặt hàng"," Hãng","Số lượng","Giá"};

    private DefaultTableModel model1 = new DefaultTableModel(COLUMN_NAMES_1,0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final String[] COLUMN_NAMES_2 = {"ID","Lô","Thêm"};

    private DefaultTableModel model2 = new DefaultTableModel(COLUMN_NAMES_2,0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == getColumnCount()-1;
        }
    };
    private Table jTable = new Table(model1);
    private JScrollPane jScrollPane = new ScrollPane(jTable);
    private ProductFormPanel productFormPanel = new ProductFormPanel(model2);
}
