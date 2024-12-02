import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrderInputForm extends JFrame {
    private JPanel contentPane;
    private JPanel namePane;
    private JPanel addressPane;
    private JTextField nameField = new JTextField();;
    private JTextField  addressField = new JTextField();;
    private JButton confirmButton;
    private JButton cancelButton;

    public OrderInputForm() {
        namePane = PanelUtils.createRoundedPanelWithTextField(10,120,50,new Font("Tahoma", Font.BOLD, 15),nameField);
        addressPane =  PanelUtils.createRoundedPanelWithTextField(10,120,100,new Font("Tahoma", Font.BOLD, 15),addressField);

        // Cài đặt Panel

        setTitle("Thông Tin Đặt Hàng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null);
        setLayout(null);

        // Tạo các thành phần giao diện
        JLabel nameLabel = new JLabel("Người đặt hàng:");
        nameLabel.setBounds(5,50,100,30);

        JLabel addressLabel = new JLabel("Địa chỉ:");
        addressLabel.setBounds(5,100,100,30);
        //Nút confirmButton
        confirmButton = new JButton("Đồng ý");
        confirmButton.setBounds(130,230,90, 25);
        confirmButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        confirmButton.setBackground(new Color(0, 123, 255));
        confirmButton.setForeground(Color.WHITE);
        //Nút  cancelButton
        cancelButton = new JButton("Hủy");
        cancelButton.setBounds(235,230,90, 25);
        cancelButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        cancelButton.setBackground(new Color(253, 73, 73));
        cancelButton.setForeground(Color.WHITE);

        //

        // Thêm các thành phần vào JFrame
        contentPane = new JPanel();
        contentPane.setBounds(0,0,350,300);
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(176,196,195,255));

        contentPane.add(nameLabel);
        contentPane.add(namePane);
        contentPane.add(addressLabel);
        contentPane.add(addressPane);
        contentPane.add(confirmButton);
        contentPane.add(cancelButton);

        add(contentPane);
        // Xử lý sự kiện nút Hủy
        cancelButtonActionPerformed();
    }


    // Xử lý sự kiện nút Đồng ý
    public void confirmButtonActionPerformed(ActionListener e) {
        confirmButton.addActionListener(e);
    }

    // Xử lý sự kiện nút Hủy
    private void cancelButtonActionPerformed() {
        cancelButton.addActionListener(e->dispose());
    }


    public JTextField getAddressField() {
        return addressField;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JTextField getNameField() {
        return nameField;
    }
}
