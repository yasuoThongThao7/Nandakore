import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeMenu extends JFrame {
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4,
                   jLabel5, jLabel6, jLabel7, jLabel8,
                   jLabel9, jLabel10, jLabel11,jLabel13;


    public WelcomeMenu() {
        super("Dự án lập trình");
        MainMenu();
    }




    public void MainMenu() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("PBL 2: Dự Án Cơ Sở Lập Trình");
        setSize(800, 420);
        setLayout(null); 
        setLocationRelativeTo(null);

        // Khởi tạo các JLabel
        jLabel1 = new JLabel(new ImageIcon("src/icons/ITBachKhoa.jpg"));
        jLabel1.setBounds(10, 100, 200, 200); // X, Y, Width, Height

        jLabel2 = new JLabel("PBL 2 : DỰ ÁN CƠ SỞ LẬP TRÌNH ");
        jLabel2.setFont(new Font("Tahoma", Font.BOLD, 20));
        jLabel2.setBounds(270, 90, 400, 30);

        jLabel3 = new JLabel("TRƯỜNG ĐẠI HỌC BÁCH KHOA ");
        jLabel3.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel3.setBounds(300, 10, 300, 20);

        jLabel4 = new JLabel("KHOA CÔNG NGHỆ THÔNG TIN ");
        jLabel4.setFont(new Font("Tahoma", Font.BOLD, 18));
        jLabel4.setBounds(270, 30, 300, 25);

        jLabel5 = new JLabel("----------------------------------------------------------------------");
        jLabel5.setBounds(270, 50, 400, 20);

        jLabel6 = new JLabel("ĐỀ TÀI : Dự án quản lý kho hàng");
        jLabel6.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel6.setBounds(270, 130, 300, 20);

        jLabel7 = new JLabel("Người hướng dẫn : GVC - Ths . Trần Hồ Thủy Tiên");
        jLabel7.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel7.setBounds(270, 160, 400, 20);

        jLabel8 = new JLabel("Người thực hiện : ");
        jLabel8.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel8.setBounds(270, 190, 200, 20);

        jLabel13 = new JLabel( "Nguyễn Thanh Liêm");
        jLabel13.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel13.setBounds(450, 190, 400, 20);

        jLabel9 = new JLabel("Dương Minh Hữu");
        jLabel9.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel9.setBounds(450, 210, 200, 20);

        jLabel10 = new JLabel("Lớp : 23T_NHAT2");
        jLabel10.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel10.setBounds(270, 250, 150, 20);

        jLabel11 = new JLabel("Nhóm học phần : 23.99A");
        jLabel11.setFont(new Font("Tahoma", Font.BOLD, 14));
        jLabel11.setBounds(450, 250, 200, 20);

        // Thêm các JLabel vào JFrame
        add(jLabel1);
        add(jLabel2);
        add(jLabel3);
        add(jLabel4);
        add(jLabel5);
        add(jLabel6);
        add(jLabel7);
        add(jLabel8);
        add(jLabel9);
        add(jLabel10);
        add(jLabel11);
        add(jLabel13);
        // Thời gian hiện
        int delay = 3000; // 3s
        Timer timer = new Timer(delay, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new MainMenu());
            }
        });
        this.setUndecorated(true);
        timer.setRepeats(false);
        timer.start();
    }
}
