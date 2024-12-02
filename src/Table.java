import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

class RoundedPanel extends JPanel {
    private int cornerRadius;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        setOpaque(false);
        setLayout(null);
        setBackground(new Color(255, 255, 255));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
    }
}

// Tạo 1 nút trong table
class EditButton extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
    private JPanel panel;
    private JButton deleteButton,editButton;
    private Table table;

    public EditButton(Table table) {
        this.table = table;
        initializePanel();
    }

    private void initializePanel() {

        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(Color.WHITE);
        //
        deleteButton = new JButton("D");
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(241, 5, 5, 121));
        deleteButton.setOpaque(true);
        //
        editButton = new JButton("E");
        editButton.setForeground(Color.WHITE);
        editButton.setBackground(new Color(24, 248, 80, 121));
        editButton.setOpaque(true);
        //
        panel.add(editButton);
        panel.add(deleteButton);
    }


    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void addDeleteButtonListener(InventoryManager instance) {
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Bạn có muốn xóa hàng này?",
                        "Delete this.",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (response == JOptionPane.YES_OPTION) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(row);
                    instance.remove(instance.getList().get(row));
                    JOptionPane.showMessageDialog(null,"Xóa thành công.","Thông báo",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    public void addEditButtonListener(ActionListener e) {
        editButton.addActionListener(e);
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(Color.WHITE);
        }
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    public JPanel getPanel() {
        return panel;
    }
}


// Lớp ScrollPane để tạo JScrollPane chứa JTable
class ScrollPane extends JScrollPane {
    public ScrollPane(JTable table) {
        super(table);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());
    }
}

// Lớp TableHeader để tùy chỉnh phần tiêu đề của bảng
class TableHeader extends JLabel {
    public TableHeader(String text) {
        super(text);
        setOpaque(true);
        setBackground(Color.WHITE);
        setFont(new Font("sansserif", Font.BOLD, 12));
        setForeground(new Color(102, 102, 102, 197));
        setBorder(new EmptyBorder(10, 40, 10, 5));
    }
}

// Lớp Table để hiển thị bảng và xử lý hiển thị và sự kiện của nút
public class Table extends JTable {
    private EditButton editButton;
    private boolean showEditButton = true;

    public Table(DefaultTableModel model) {
        super(model);
        this.editButton = new EditButton(this);
        initialize();
    }

    private void initialize() {
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(false);
        getTableHeader().setBorder(null);

        // Thiết lập cho TableHeader
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return new TableHeader(value.toString());
            }
        });

        // Thiết lập renderer cho từng ô trong bảng
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (showEditButton && column == table.getColumnCount() - 1) {
                    return editButton.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(Color.WHITE);
                setBorder(noFocusBorder);
                setHorizontalAlignment(JLabel.CENTER);
                component.setForeground(isSelected ? new Color(15, 89, 140) : new Color(102, 102, 102));
                return component;
            }
        });

        // Thiết lập Editor cho cột chứa nút
        setAutoCreateRowSorter(true);
        getColumnModel().getColumn(getColumnCount() - 1).setCellEditor(editButton);
        setShowHorizontalLines(true);
        setRowHeight(40);
        setBorder(null);
        setShowVerticalLines(false);
    }

    public EditButton getEditButton() {
        return editButton ;
    }

    public void setShowEditButton(boolean show) {
        this.showEditButton = show;
        repaint();
    }
}

