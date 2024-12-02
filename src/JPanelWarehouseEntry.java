import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;

public class JPanelWarehouseEntry extends JPanelMini {
    private final JLabel text = new JLabel("Nhập kho");
    private final JLabel image = new JLabel();
    public JPanelWarehouseEntry() {
        //image
        image.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/vaokho.png"))));
        image.setBounds(20,2,48,48);
        add(image);
        //text
        text.setFont(new Font("Tahoma", Font.BOLD, 15));
        text.setBounds(130, 10, 100, 30);
        add(text);
        // Thiết lập JPanelHome
        setBounds(0, 310, 280, 60);
        setLayout(null);
        setBackground(new Color(25, 188, 211));
    }
}

class WarehouseEntryMenu extends JPanel {
    private final String[] COLUMN_NAMES = {"ID","Mặt hàng","Hãng","Số lượng","Giá nhập","Điều chỉnh"};
    private JScrollPane jScrollPane;
    private JPanel panelTable,panelText;
    private final AddPanel addProductPanel =  new AddPanel(10);
    private final DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES,0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            if(column != getColumnCount()-1)
                return false;
            return true;
        }
    };
    private Table table = new Table(model);
    private static WarehouseEntryMenu instance = new WarehouseEntryMenu();


    private WarehouseEntryMenu() {
            initialize();
            submitAction();
            editAction();
            table.getEditButton().addDeleteButtonListener(WarehouseEntryFactory.getInstance());
    }

    private void initialize() {
        // Panel chứa JTextField
        panelText = new JPanel();
        panelText.setLayout(null);
        panelText.setBackground(new Color(25, 188, 211));

        //Thiết lập JTable
        if( WarehouseEntryFactory.getInstance() != null) {
            for(Import order : WarehouseEntryFactory.getInstance().getList()){
                model.addRow(makeRow(order));
            }
        }
        // Tên table
        JLabel label = new JLabel("DANH SÁCH HÀNG NHẬP : ");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(10, 10, 250, 30);
        label.setForeground(new Color(102, 102, 102, 252));
        // Table
        jScrollPane = new ScrollPane(table);
        jScrollPane.setBounds(25, 50, 750, 303);
        panelTable = new RoundedPanel(30);
        panelTable.setBounds(50,280,800,350);
        panelTable.add(jScrollPane);
        panelTable.add(label);

        // Thiết lập JPanel
        add(addProductPanel);
        add(panelTable);
        setLayout(null);
        setBackground(new Color(176,196,195,255));
        setBounds(280, 0, 920, 680);
    }


    private void setText(int row){
        String supName =  table.getValueAt(row,2).toString();
        addProductPanel.setText(row,supName);
    }
//Hàm sự kiện của nút edit
    public void editAction() {
        table.getEditButton().addEditButtonListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                setText(selectedRow);
                addProductPanel.setCount(1);
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn một hàng để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
//Hàm sự kiện của nút submit
    public void submitAction() {
        addProductPanel.jButtonAction(e -> {
            try {
                if (addProductPanel.getCount() == 0) {
                    Import order = addProductPanel.make();// Kiểm tra nếu có lỗi đầu vào
                    model.addRow(makeRow(order));
                    WarehouseEntryFactory.getInstance().add(order);
                    WarehouseMenu.getInstance().addTableData(order);
                    JOptionPane.showMessageDialog(this, "Thêm thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    int row = table.getSelectedRow();
                    if (row != -1) {
                        addProductPanel.setCount(0);
                        setRow(row,makeRow(addProductPanel.set(row)));
                        WarehouseMenu.getInstance().setTableData(row,model.getValueAt(row,1).toString());
                        JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (NumberFormatException nfe){
                JOptionPane.showMessageDialog(this,nfe.getMessage(),"Lôi nhập liệu",JOptionPane.ERROR_MESSAGE);
            } catch (TextFieldException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } finally {
                addProductPanel.resetText();
                addProductPanel.setCount(0);
            }
        });

    }

    void setRow(int row,Object[] o){
        for(int i = 1 ; i < table.getColumnCount() - 1; i++){
             table.setValueAt(o[i],row,i);
        }
    }

    public Table getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }

    // add product
    public Object[] makeRow (Import i){
        return new Object[]{
                    i.getID(),
                    i.getProductName(),
                    SupplierFactory.getInstance().getSuppliersName(i.getSupplierID()),
                    i.getStringQuantity(),
                    String.format("%s/%s" ,i.getPrice_in(),i.getUnitName()),

        };
    }

    public  static WarehouseEntryMenu getInstance() {
        return instance;
    }

}


// Tạo panel thêm Đối Tượng trong table
class AddPanel extends RoundedPanel implements JButtonAction {


    private JLabel titleLabel;
    private JPanel titlePanel, panelButton, panelComboBox;
    private JTextField textField1, textField2, textField3, textField4;
    private JButton submitButton;
    private final JComboBox<String> comboBox = new JComboBox<>(Unit.unit_name);

    private int count;

    public AddPanel(int cornerRadius) {
        super(cornerRadius);
        initialize();
        this.count = 0;
    }

    public void initialize() {
        // Panel tiêu đề
        titleLabel = LabelUtils.createLabel("Thêm Mặt Hàng", 15, Color.WHITE);
        titlePanel = new RoundedPanel(10);
        titlePanel.setLayout(null);
        titlePanel.setBounds(5, 7, 317, 35);
        titlePanel.setBackground(new Color(63, 81, 93));
        titleLabel.setBounds(50, 3, 200, 30);
        titlePanel.add(titleLabel);
        add(titlePanel);

        // Tạo các phần tử giao diện
        textField1 = new JTextField();
        textField2 = new AutocompleteExample(SupplierFactory.getInstance().getListNames());
        textField3 = new JTextField();
        textField4 = new JTextField();

        add(PanelUtils.createRoundedPanelWithLabel("Mặt Hàng", 15, 10, 60, new Color(63, 81, 93)));
        add(PanelUtils.createRoundedPanelWithTextField(10, 120, 60, new Font("Tahoma", Font.BOLD, 15), textField1));

        add(PanelUtils.createRoundedPanelWithLabel("Hãng", 15, 10, 105, new Color(63, 81, 93)));
        add(PanelUtils.createRoundedPanelWithTextField(10, 120, 105, new Font("Tahoma", Font.BOLD, 15), textField2));

        add(PanelUtils.createRoundedPanelWithLabel("Số Lượng", 15, 10, 150, new Color(63, 81, 93)));
        add(PanelUtils.createRoundedPanelWithTextField(10, 120, 150,83,30, new Font("Tahoma", Font.BOLD, 15), textField3));

        add(PanelUtils.createRoundedPanelWithLabel("Giá Nhập", 15, 10, 190, new Color(63, 81, 93)));
        add(PanelUtils.createRoundedPanelWithTextField(10, 120, 190,83,30, new Font("Tahoma", Font.BOLD, 15), textField4));

        // JButton và JPanel chứa nó
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(new Color(0, 102, 204));
        submitButton.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        submitButton.setOpaque(false);

        panelButton = new RoundedPanel(10);
        panelButton.setBounds(210, 190, 100, 30);
        panelButton.setLayout(new BorderLayout());
        panelButton.add(submitButton, BorderLayout.CENTER);
        panelButton.setBackground(new Color(0, 102, 204));
        add(panelButton);

        // JComboBox
        comboBox.setOpaque(false);
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 15));
        comboBox.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        comboBox.setBackground(Color.WHITE);

        panelComboBox = new RoundedPanel(10);
        panelComboBox.setBounds(210, 150, 100, 30);
        panelComboBox.setLayout(new BorderLayout());
        panelComboBox.add(comboBox, BorderLayout.CENTER);
        add(panelComboBox);

        // Thiết lập giao diện tổng thể
        setBounds(50, 15, 330, 250);
        setLayout(null);
        setBackground(new Color(31, 167, 189, 255));
    }
// Hàm check ngoại lệ
private void validateInputs() throws TextFieldException {
    if (textField1.getText().isEmpty()) {
        throw new TextFieldException("Mặt hàng không được để trống!");
    }
    //Check Hãng
    if (textField2.getText().isEmpty()) {
        throw new TextFieldException("Hãng không được để trống!");
    } else if (!SupplierFactory.getInstance().isContains(textField2.getText().trim())) {
        throw new TextFieldException("Hãng không có trong danh sách!");
    }
    //Check số lượng
    if (textField3.getText().isEmpty()) {
        throw new TextFieldException("Số lượng không được để trống!");
    } else {
        int selectedIndex = comboBox.getSelectedIndex();
        if (selectedIndex % 2 != 0) {
            double tmp = Double.parseDouble(textField3.getText())-Integer.parseInt(textField3.getText());
            if(Double.compare(tmp,0) != 0){
                throw new TextFieldException("Số lượng không hợp lệ");
            }
        }
    }
    // Check giá nhập
    if (textField4.getText().isEmpty()) {
        throw new TextFieldException("Giá nhập không được để trống!");
    } else {
            double tmp = Double.parseDouble(textField4.getText()) - Integer.parseInt(textField4.getText());
            if (Double.compare(tmp, 0) != 0) {
                throw new TextFieldException("Số lượng không hợp lệ");
            }
    }
}


    // Hàm tạo đối tượng
    public Import make() throws TextFieldException {
        // Gọi hàm kiểm tra
        validateInputs();

        // Nếu không có lỗi, tiếp tục xử lý tạo đối tượng
        String nameProduct = textField1.getText();
        String supplierId = SupplierFactory.getInstance().search(textField2.getText());
        double quantity = Double.parseDouble(textField3.getText());
        int priceIn = Integer.parseInt(textField4.getText());
        int unit = comboBox.getSelectedIndex();
        // trả về đối tượng
        return new Import(nameProduct, priceIn, supplierId, unit, quantity);

    }
// Hàm set đối tượng và trả về đối tương được set
    public Import set(int row) throws TextFieldException {
        // Gọi hàm kiểm tra
        validateInputs();

        // Nếu không có lỗi
        String nameProduct = textField1.getText();
        String supplierId = SupplierFactory.getInstance().search(textField2.getText());
        double quantity = Double.parseDouble(textField3.getText());
        int priceIn = Integer.parseInt(textField4.getText());
        int unit = comboBox.getSelectedIndex();
        Import i = WarehouseEntryFactory.getInstance().getList().get(row);
        i.setFull(nameProduct,priceIn,supplierId,unit,quantity);
        WarehouseEntryFactory.getInstance().setFile();

        return i;
    }


    @Override
    public void resetText() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        comboBox.setSelectedIndex(0);
    }

    @Override
    public void jButtonAction(ActionListener e) {
        submitButton.addActionListener(e);
    }

    public void setText(int row,String supNames) {
        Import i = WarehouseEntryFactory.getInstance().getList().get(row);
        textField1.setText(i.getProductName());
        textField2.setText(supNames);
        textField3.setText(i.getStringQuantity());
        textField4.setText(i.getPrice_in()+"");
        comboBox.setSelectedIndex(i.getUnitIndex());
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
