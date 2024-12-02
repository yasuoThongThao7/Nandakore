import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


abstract class JPanelMini extends JPanel {
    private boolean k = false;

    public boolean getK() {
        return k;
    }

    public void setK(boolean k) {
        this.k = k;
    }
}

class JPanelMenu extends JPanel {
    public JPanelMenu() {
        setLayout(null);
        setBounds(0, 0, 280, 750);
        setBackground(new Color(25, 188, 211));
    }
}

public class MainMenu extends JFrame {
    private JPanel[] menuPanels;
    private JPanelMini[] mainPanels;

    public MainMenu() {
        menu();

    }

    public void menu() {
        JPanel jPanelMenu = new JPanelMenu();
        //thêm logo
        JLabel jLabelLogo = new JLabel("");
        jLabelLogo.setIcon(new ImageIcon("src/icons/liem.png"));
        jLabelLogo.setBounds(45,10,175,200);
        jPanelMenu.add(jLabelLogo);
        //tạo vạch
        JPanel jPanelVach = new JPanel();
        jPanelVach.setBounds(45,205,200,2);
        jPanelVach.setBackground(Color.BLACK);
        jPanelMenu.add(jPanelVach);
        // Tạo các JPanel chính và menu
        JPanelMini jPanelHome = new JPanelHome();
        JPanelMini jPanelSupplier = new JPanelSupplier();
        JPanelMini jPanelWarehouse = new JPanelWarehouse();
        JPanelMini jPanelEntryWarehouse = new JPanelWarehouseEntry();
        JPanelMini jPanelWarehouseExport = new JPanelWarehouseExport();

        // Đặt các JPanel vào mảng
        menuPanels = new JPanel[]{    JPanelHomeMenu.getInstance(),
                                      JPanelSupplierMenu.getInstance(),
                                      WarehouseMenu.getInstance(),
                                      WarehouseEntryMenu.getInstance(),
                                      WarehouseExportMenu.getInstance()
        };

        mainPanels = new JPanelMini[]{jPanelHome,
                                      jPanelSupplier,
                                      jPanelWarehouse,
                                      jPanelEntryWarehouse,
                                      jPanelWarehouseExport};

        // Tắt và thêm tất cả các menu khi khởi động
        add(jPanelMenu);
        for (JPanel panel : menuPanels) {
            add(panel);
            panel.setVisible(false);
        }

        // Thêm các JPanel chính vào jPanelMenu

        for (JPanel panel : mainPanels) {
            jPanelMenu.add(panel);
        }

        // Thêm sự kiện panel
        for(int i = 0 ; i < 5 ; i++){
            addListeners(mainPanels[i],menuPanels[i]);
        }
        //Mặc định cho menu home
        menuPanels[0].setVisible(true);
        jPanelHome.setBackground(new Color(43, 125, 136));
        jPanelHome.setK(true);
        // Thiết lập JFrame
        setLayout(null);
        setSize(1200, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Hàm để thêm sự kiện và ẩn tất cả các panel khác
    public void addListeners(JPanelMini panelMain, JPanel panelMenu) {
        panelMain.addMouseListener(new MouseAdapter() {

            public void mouseEntered(MouseEvent evt) {
                if(!panelMain.getK())
                    panelMain.setBackground(new Color(43, 125, 136));
            }

            public void mouseExited(MouseEvent evt) {
                if(!panelMain.getK())
                    panelMain.setBackground(new Color(25, 188, 211));
            }

            public void mouseClicked(MouseEvent evt) {
                if(!panelMain.getK()){
                    for (JPanel panel : menuPanels) {
                        panel.setVisible(false);
                    }
                    for (JPanelMini panel : mainPanels) {
                        panel.setBackground(new Color(25, 188, 211));
                        panel.setK(false);
                    }
                    panelMain.setBackground(new Color(43, 125, 136));
                    panelMain.setK(true);
                    panelMenu.setVisible(true);
                }
            }

        });
    }

}



