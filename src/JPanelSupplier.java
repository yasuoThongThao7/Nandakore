import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;


public class JPanelSupplier extends JPanelMini{
    private JLabel text = new JLabel("Nhà cung cấp");
    private JLabel image = new JLabel();

    public JPanelSupplier () {
        image.setIcon(new ImageIcon("src/icons/nhacungcap.png"));
        image.setBounds(20,2,48,48);
        add(image);
        //text
        text.setFont(new Font("Tahoma", Font.BOLD, 15));
        text.setBounds(130, 10, 130, 30);
        add(text);
        // Thiết lập JPanelHome
        setBounds(0, 490, 280, 60);
        setLayout(null);
        setBackground(new Color(25, 188, 211));
    }
}

class JPanelSupplierMenu extends JPanel {

    private static JPanelSupplierMenu instance = new JPanelSupplierMenu();

    private JPanelSupplierMenu() {
        settingJTable();
        initialize();
        editAction();
        submitAction();
        table.getEditButton().addDeleteButtonListener(SupplierFactory.getInstance());
    }

    public void initialize() {
        panelText = new JPanel();
        panelText.setLayout(null);
        panelText.setBackground(new Color(25, 188, 211));
        // Thiết lập JPanel

        setBackground(new Color(176,196,195,255));
        add(panelText);
        add(panelTable);
        add(addPanel);
        setLayout(null);
        setBackground(new Color(239, 239, 239, 50));
        setBounds(280, 0, 920, 680);
    }


    private void settingJTable(){
        JLabel label = new JLabel("DANH SÁCH NHÀ CUNG CẤP :");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(10, 10, 250, 30);
        label.setForeground(new Color(102, 102, 102, 252));

        if( SupplierFactory.getInstance() != null) {
            for(Supplier supplier : SupplierFactory.getInstance().getList()) {
                model.addRow(makeRow(supplier));
            }
        }
        table = new Table(model);

        jScrollPane = new ScrollPane(table);
        jScrollPane.setBounds(25, 50, 750, 303);
        panelTable = new RoundedPanel(30);
        panelTable.setBounds(50,280,800,350);
        panelTable.add(jScrollPane);
        panelTable.add(label);

    }




    private Object[] makeRow(Supplier supplier) {
        return new Object[]{
                supplier.getSupplier_id(),
                supplier.getName(),
                supplier.getAddress(),
                supplier.getEmail(),
                supplier.getPhone()
        };
    }



    private void setText() {
        int viewIndex = table.getSelectedRow();
        if (viewIndex != -1) {
            int modelIndex = table.convertRowIndexToModel(viewIndex);

            String text1 = table.getValueAt(modelIndex, 1).toString();
            String text2 = table.getValueAt(modelIndex, 2).toString();
            String text3 = table.getValueAt(modelIndex, 3).toString();
            String text4 = table.getValueAt(modelIndex, 4).toString();

            addPanel.setText(text1, text2, text3, text4);
        }
    }



    public void editAction() {
        table.getEditButton().addEditButtonListener(e -> {
            int viewIndex = table.getSelectedRow();
            if (viewIndex != -1) { // Kiểm tra dòng đã được chọn
                setText();
                addPanel.setCount(1); // Đánh dấu trạng thái chỉnh sửa
            } else {
                JOptionPane.showMessageDialog(this, "Hãy chọn một hàng để chỉnh sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    public void submitAction() {
        addPanel.jButtonAction(e -> {
            try {
                if (addPanel.getCount() == 0) { // Thêm mới
                    Supplier sup = addPanel.make();
                    SwingUtilities.invokeLater(() -> {
                        model.addRow(makeRow(sup));
                        SupplierFactory.getInstance().add(sup);
                    });
                } else { // Chỉnh sửa
                    int viewIndex = table.getSelectedRow();
                    if (viewIndex != -1) {
                        int modelIndex = table.convertRowIndexToModel(viewIndex); // Chuyển view sang model
                        SwingUtilities.invokeLater(() -> {
                            Supplier sup = null;
                            try {
                                sup = addPanel.set(modelIndex);
                                setRow(modelIndex, makeRow(sup));
                            } catch (TextFieldException ex) {
                                throw new RuntimeException(ex);
                            }
                            SupplierFactory.getInstance().set(modelIndex, sup);
                        });
                        addPanel.setCount(0);
                    }
                }
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } catch (TextFieldException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            } finally {
                addPanel.resetText();
            }

        });
    }


    private void setRow(int row, Object[] o){
        for(int i = 1 ; i < o.length ; i++ ) {
                table.setValueAt(o[i],row,i);
        }
    }


    public static JPanelSupplierMenu getInstance() {
        return instance;
    }

    private final String[] COLUMN_NAMES= {"ID", "NCC", "Địa chỉ", "Email", "SĐT", "Điều chỉnh"};
    private Table table;
    private JScrollPane jScrollPane;
    private JPanel panelTable, panelText;
    private final AddSupplierPanel addPanel = new AddSupplierPanel(10);
    private DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0){
        @Override
        public boolean isCellEditable(int row , int column){
            if(column != getColumnCount()-1)
                return false;
            return true;
        }
    };
}


class AddSupplierPanel extends RoundedPanel implements JButtonAction {
    private int count;
    private JLabel jLabel;
    private JPanel jPanel,jPanel1,jPanel2, jPanel3,
                   jPanel4,jPanelTextField1, jPanelTextField2,
                   jPanelTextField3,jPanelTextField4,jPanelButton;
    private JTextField jTextField1, jTextField2,jTextField3,jTextField4;
    private JButton jButton;


    public AddSupplierPanel(int cornerRadius) {
        super(cornerRadius);
        initialize();
    }

    private void initialize() {
        // JLabel tiêu đề
        jLabel = LabelUtils.createLabel("Thêm nhà cung cấp", 15, Color.WHITE);
        jLabel.setBounds(50, 3, 200, 30);

        // Panel tiêu đề
        jPanel = new RoundedPanel(10);
        jPanel.setLayout(null);
        jPanel.setBounds(5, 7, 317, 35);
        jPanel.setBackground(new Color(63, 81, 93));
        jPanel.add(jLabel);
        add(jPanel);

        // Panel và TextField "NCC"
        jTextField1 = new JTextField();
        jPanel1 = PanelUtils.createRoundedPanelWithLabel("NCC", 15, 10, 60, new Color(63, 81, 93, 255));
        jPanelTextField1 = PanelUtils.createRoundedPanelWithTextField(10, 120, 60, new Font("Tahoma", Font.BOLD, 15), jTextField1);
        add(jPanel1);
        add(jPanelTextField1);

        // Panel và TextField "Địa chỉ"
        jTextField2 = new JTextField();
        jPanel2 = PanelUtils.createRoundedPanelWithLabel("Địa chỉ", 15, 10, 105, new Color(63, 81, 93, 255));
        jPanelTextField2 = PanelUtils.createRoundedPanelWithTextField(10, 120, 105, new Font("Tahoma", Font.BOLD, 15), jTextField2);
        add(jPanel2);
        add(jPanelTextField2);

        // Panel và TextField "Email"
        jTextField3 = new JTextField();
        jPanel3 = PanelUtils.createRoundedPanelWithLabel("Email", 15, 10, 150, new Color(63, 81, 93, 255));
        jPanelTextField3 = PanelUtils.createRoundedPanelWithTextField(10, 120, 150, new Font("Tahoma", Font.BOLD, 15), jTextField3);
        add(jPanel3);
        add(jPanelTextField3);

        // Panel và TextField "SĐT"
        jTextField4 = new JTextField();
        jPanel4 = PanelUtils.createRoundedPanelWithLabel("SĐT", 15, 10, 190, new Color(63, 81, 93, 255));
        jPanelTextField4 = PanelUtils.createRoundedPanelWithTextField(10, 120, 190, 83,30, new Font("Tahoma", Font.BOLD, 15), jTextField4);
        add(jPanel4);
        add(jPanelTextField4);

        // Nút "Submit"
        createSubmitButton();
        // Set Panel
        setBounds(50, 15, 330, 250);
        setLayout(null);
        setBackground(new Color(31, 167, 189, 255));
    }



    private void createSubmitButton() {
        jButton = new JButton("Submit");
        jButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(new Color(0, 102, 204));
        jButton.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        jButton.setOpaque(false);

        jPanelButton = new RoundedPanel(10);
        jPanelButton.setBounds(210, 190, 100, 30);
        jPanelButton.setLayout(new BorderLayout());
        jPanelButton.add(jButton, BorderLayout.CENTER);
        jPanelButton.setBackground(new Color(0, 102, 204));
        add(jPanelButton);
    }


// Hàm check ngoại lệ
    private void validateInputs() throws TextFieldException{
        if (jTextField1.getText().trim().isEmpty()) {
            throw new TextFieldException("Tên không được để trống.");
        }
        if (!jTextField3.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new TextFieldException("Địa chỉ email không hợp lệ");
        }
        if (jTextField2.getText().trim().isEmpty()) {
            throw new TextFieldException("Địa chỉ không được để trống.");
        }

        if (!jTextField4.getText().trim().matches("\\d{8,15}")) {
            throw new TextFieldException("Số điện thoại phải là 8-15 chữ số.");
        }
    }

// Hàm tạo đối tượng

    public Supplier make() throws TextFieldException {
        validateInputs();// Check ngoaij lệ
    // Thực hiện hàm
        String name  = jTextField1.getText().trim();
        String address = jTextField2.getText().trim();
        String email = jTextField3.getText().trim();
        String phone = jTextField4.getText().trim();


        return new Supplier(name,address,phone,email);
    }

// Hàm set Đối tượng và trả về khi chỉnh sửa
    public Supplier set(int row) throws TextFieldException{

        validateInputs();// Check ngoại lệ

        // Thực hiện hàm
        String name  = jTextField1.getText().trim();
        String address = jTextField2.getText().trim();
        String email = jTextField3.getText().trim();
        String phone = jTextField4.getText().trim();

        // Sửa lại đối tượng 2 list
        Supplier sup =  SupplierFactory.getInstance().getList().get(row);
        sup.setFull(name,address,phone,email);
        SupplierFactory.getInstance().setFile();
        return sup;// Trả về sup
    }


// Hàm set lại các text
    public void setText(String text1,String text2,String text3,String text4) {
        jTextField1.setText(text1);
        jTextField2.setText(text2);
        jTextField3.setText(text3);
        jTextField4.setText(text4);
    }

    @Override
    public void resetText() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
    }

    @Override
    public void jButtonAction(ActionListener e) {
        jButton.addActionListener(e);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}


