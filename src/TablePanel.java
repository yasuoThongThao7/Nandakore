import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TablePanel extends JPanel  {

    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JLabel label ;

    public TablePanel(String[] columnNames, JLabel label) {
        this.tableModel = new DefaultTableModel(columnNames,0);
        this.label = label;
        setThis();
    }

    private void setThis(){
        scrollPane.setBounds(25, 50, 750, 303);
    }

    public void makeRow(InventoryManager i) {

    }

    public void addRow(Object[] row) {
        tableModel.addRow(row);
    }

}
