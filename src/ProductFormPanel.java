import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;



public class ProductFormPanel extends RoundedPanel {
    private JTable table;
    private JScrollPane scrollPane;
    private SearchPanel search ;
    private JButton Add = new JButton("Add");
    private DefaultTableModel model;

    public ProductFormPanel(DefaultTableModel model) {
        super(30);
        this.model = model;
        setTable();
        initialize();
    }

    private void initialize(){
        search.setBounds(15,5,575,30);

        scrollPane = new ScrollPane(table);
        scrollPane.setBounds(15,45,575,195);

        Add.setFont(new Font("Tahoma", Font.BOLD, 14));
        Add.setBackground(new Color(0, 123, 255));
        Add.setForeground(Color.WHITE);
        Add.setBounds(320,245,70, 25);

        setBackground(new Color(25, 188, 211));
        setBounds(50,3,600,275);
        setLayout(null);
        add(search);
        add(scrollPane);
        add(Add);
    }


    private void setTable() {
        table = new JTable(model);
        search = new SearchPanel(table);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setBorder(null);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableHeader header = new TableHeader(value + "");
                return header;
            }
        });

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(Color.WHITE);
                if (isSelected) {
                    component.setForeground(new Color(15, 89, 140));
                } else {
                    component.setForeground(new Color(102, 102, 102));
                }
                return component;
            }
        });



        table.getColumnModel().getColumn(table.getModel().getColumnCount() - 1).setCellEditor(new DefaultCellEditor(new JCheckBox()));

        table.getColumnModel().getColumn(table.getModel().getColumnCount() - 1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected(Boolean.TRUE.equals(value));
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                checkBox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                return checkBox;
            }
        });
    }



    public JButton getAdd() {
        return Add;
    }

    public DefaultTableModel getModel(){
        return model;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public SearchPanel getSearch() {
        return search;
    }

    public JTable getTable() {
        return table;
    }

    public void addButtonActionListener(ActionListener actionListener){
        Add.addActionListener(actionListener);
    }


}