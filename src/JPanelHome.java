import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class JPanelHome extends JPanelMini {
    private JLabel text = new JLabel("Trang chủ");
    private JLabel image = new JLabel();

    public JPanelHome() {
        //image
        image.setIcon(new ImageIcon("src/icons/nhane.png"));
        image.setBounds(20,2,48,48);
        add(image);
        //text
        text.setFont(new Font("Tahoma", Font.BOLD, 15));
        text.setBounds(130, 10, 100, 30);
        add(text);
        // Thiết lập JPanelHome
        setBounds(0, 250, 280, 60);
        setLayout(null);
        setBackground(new Color(25, 188, 211));

    }
}
class JPanelHomeMenu extends JPanel {

    private static JPanelHomeMenu instance = new JPanelHomeMenu();

    private JPanelHomeMenu() {
        initialize();
        buttonAction();
    }

    private void initialize() {
        // set icon
        icon = new JLabel();
        icon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/user1.png"))));
        icon.setBounds(375,25,180,180);
        // Set label
        nickName.setFont(new Font("Tahoma", Font.BOLD, 18));
        nickName.setBounds(420, 200, 100, 30);

        // Set button
        setPassWord.setFont(new Font("Tahoma", Font.BOLD, 15));
        setPassWord.setForeground(Color.WHITE);
        setPassWord.setBackground(new Color(54, 174, 189));
        setPassWord.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));

        panelButton = new RoundedPanel(10);
        panelButton.setBounds(393, 260, 120, 40);
        panelButton.setLayout(new BorderLayout());
        panelButton.add(setPassWord, BorderLayout.CENTER);
        panelButton.setBackground(new Color(54, 174, 189));

        // Reset password panel - hidden by default
        resetPassWord.setVisible(false);
        add(resetPassWord);

        // Add components to main panel
        setLayout(null);
        add(icon);
        add(nickName);
        add(panelButton);
        setBackground(new Color(239, 239, 239));
        setBounds(280, 0, 920, 680);
    }

    private void buttonAction() {
        setPassWord.addActionListener(e -> resetPassWord.setVisible(!resetPassWord.isVisible()));
    }

    public static JPanelHomeMenu getInstance() {
        return instance;
    }

    // Components
    private JLabel icon ;
    private JLabel nickName = new JLabel("Admin");
    private JPanel panelButton;
    private ResetPassWord resetPassWord = new ResetPassWord();
    private JButton setPassWord = new JButton("Đổi mật khẩu.");
}

class ResetPassWord extends RoundedPanel {

    private JPasswordField password1, password2, password3;
    private JLabel label1, label2, label3;
    private JButton confirmButton, cancelButton;

    public ResetPassWord() {
        super(30);
        initialize();
        addActions();
    }

    private void initialize() {
        // Set pass1
        label1 = new JLabel("Mật khẩu cũ:");
        label1.setBounds(95,50,150,30);
        label1.setFont(new Font("Tahoma", Font.BOLD, 15));

        password1 = new JPasswordField();
        password1.setBackground(Color.WHITE);
        password1.setForeground(Color.BLACK);
        password1.setBounds(233, 45, 200, 30);
        password1.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));

        //Set pass2
        label2 = new JLabel("Mật khẩu mới:");
        label2.setBounds(84,100,200,30);
        label2.setFont(new Font("Tahoma", Font.BOLD, 15));
        password2 = new JPasswordField();
        password2.setBackground(Color.WHITE);
        password2.setForeground(Color.BLACK);
        password2.setBounds(233, 95, 200, 30);
        password2.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));


        //Set pass3
        label3 = new JLabel("Nhập lại mật khẩu:");
        label3.setBounds(50,150,150,30);
        label3.setFont(new Font("Tahoma", Font.BOLD, 15));
        password3 = new JPasswordField();
        password3.setBackground(Color.WHITE);
        password3.setForeground(Color.BLACK);
        password3.setBounds(233,145 , 200, 30);
        password3.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
         //
        cancelButton = new JButton("Hủy");
        cancelButton.setBounds(300,212,90,30);
        cancelButton.setBackground(new Color(228, 105, 105));

        confirmButton = new JButton("Xác nhận");
        confirmButton.setBounds(400,212,90,30);
        confirmButton.setBackground(new Color(90, 214, 227));
        // Set và add
        setLayout(null);
        add(label1);
        add(password1);
        add(label2);
        add(password2);
        add(label3);
        add(password3);
        add(confirmButton);
        add(cancelButton);

        setBackground(Color.WHITE);
        setBounds(210, 330, 500, 250);
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
        field.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        return field;
    }

    private void addActions() {
        confirmButton.addActionListener(e ->{
            try{
                handlePasswordChange();
                JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                password1.setText("");
                password2.setText("");
                password3.setText("");
                setVisible(false);
            }catch(TextFieldException tfe){
                JOptionPane.showMessageDialog(this,tfe.getMessage(),"Lôi nhập liệu",JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e ->{
            password1.setText("");
            password2.setText("");
            password3.setText("");
            setVisible(false);
        });
    }
    private void validateInputs() throws TextFieldException{
        String oldPassword = new String(password1.getPassword());
        String newPassword = new String(password2.getPassword());
        String confirmPassword = new String(password3.getPassword());

        if (oldPassword.isEmpty()||newPassword.isEmpty()||confirmPassword.isEmpty()) {
            throw new TextFieldException("Mật khẩu không được để trống!");
        }

        String pattern = "^[0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?~`]+$";
        if(!newPassword.matches(pattern)) {
            throw new TextFieldException("Mật khẩu phải chứa các ký tự 0-9, a-z, A-Z và ký tự đặc biệt như !@#$%...");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new TextFieldException("Mật khẩu mới không khớp với mật khẩu vừa nhập !");
        }

        if(!AdminService.getInstance().check(oldPassword)){
            throw new TextFieldException("Mật khẩu cũ không đúng !");
        }
    }

    private void handlePasswordChange() throws TextFieldException {
        validateInputs();// Check ngoại lệ
        String confirmPassword = new String(password3.getPassword());
        AdminService.getInstance().setPassword(confirmPassword);
    }
}
