import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *      @author admin
 **/
public class SignIn extends JFrame {

    private JButton jButton1;
    private JLabel password;
    private JPasswordField password_text;
    private JLabel username;
    private JTextField username_text;
    private JLabel errorMessageLabel;
    private JLabel signInLabel;
    private JLabel hello;
    private JButton closeButton;


    public SignIn() {
        super("Sign In");
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JPanel jPanel1 = new JPanel(null);
        jPanel1.setBounds(0, 0, 350, 500);
        jPanel1.setBackground(new Color(25, 188, 211));

        closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBackground(new Color(25, 188, 211));
        closeButton.setBorder(null);
        closeButton.setBounds(370, 10, 20, 20);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });

        signInLabel = new JLabel("Đăng nhập");
        signInLabel.setForeground(Color.WHITE);
        signInLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        signInLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signInLabel.setBounds(100, 30, 200, 50);

        hello = new JLabel("Chào mừng đến với hệ thống quản lý kho hàng!");
        hello.setForeground(Color.WHITE);
        hello.setFont(new Font("Tahoma", Font.PLAIN, 14 ));
        hello.setHorizontalAlignment(SwingConstants.CENTER);
        hello.setBounds(27, 65, 350, 50);

        username = new JLabel("Tên đăng nhập:");
        username.setFont(new Font("Tahoma", Font.PLAIN, 14));
        username.setForeground(Color.WHITE); // Đặt màu chữ thành trắng
        username.setBounds(20, 130, 150, 30);

        username_text = new JTextField();
        username_text.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        username_text.setBackground(new Color(25, 188, 211));
        username_text.setForeground(Color.WHITE);
        username_text.setBounds(150, 130, 150, 30);

        password = new JLabel("Mật khẩu:");
        password.setFont(new Font("Tahoma", Font.PLAIN, 14));
        password.setForeground(Color.WHITE); // Đặt màu chữ thành trắng
        password.setBounds(57, 180, 100, 30);

        password_text = new JPasswordField();
        password_text.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        password_text.setBackground(new Color(25, 188, 211));
        password_text.setForeground(Color.WHITE);
        password_text.setBounds(150, 180, 150, 30);

        errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setBounds(50, 220, 300, 30);

        jButton1 = new JButton("LOGIN");
        jButton1.setFont(new Font("Tahoma", Font.BOLD, 16));
        jButton1.setBackground(new Color(255, 255, 255));
        jButton1.setForeground(new Color(0, 102, 204));
        jButton1.setBounds(148, 270, 100, 40);

        // Thêm các thành phần vào JPanel
        jPanel1.add(signInLabel);
        jPanel1.add(username);
        jPanel1.add(username_text);
        jPanel1.add(password);
        jPanel1.add(password_text);
        jPanel1.add(errorMessageLabel);
        jPanel1.add(jButton1);
        jPanel1.add(hello);
        jPanel1.add(closeButton);
        // Thêm sự kiện
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        username_text.addActionListener(evt -> textActionPerformed(evt));
        password_text.addActionListener(evt -> textActionPerformed(evt));

        // Set JFrame
        this.setUndecorated(true);
        this.add(jPanel1);
        this.setSize(400, 365);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void jButton1ActionPerformed(ActionEvent evt) {
        if (AdminService.getInstance().check(username_text.getText(), password_text.getText())) {
            dispose();
            new WelcomeMenu().setVisible(true);
        } else {
            errorMessageLabel.setText("Mật khẩu hoặc tên đăng nhập sai, vui lòng thử lại.");
            password_text.setText("");
        }
    }

    public void textActionPerformed(ActionEvent evt) {
        jButton1.doClick();
    }

}
