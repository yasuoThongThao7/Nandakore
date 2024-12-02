import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

public class SearchPanel extends JTextField {
    private final JTable table;

    public SearchPanel(JTable table) {
        this.table = table;
        setTextField();
        addListeners();
        if(table != null){
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.table.getModel());
            table.setRowSorter(sorter);
            initComponents(sorter);
        }
    }

    private void setTextField() {
        setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        setBackground(new Color(93, 219, 234, 255));
        setForeground(new Color(102, 102, 102, 74));
        setToolTipText("Nhập từ khóa để tìm kiếm...");
        setText("Tìm kiếm....");
    }

    private void addListeners() {
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals("Tìm kiếm....")) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText("Tìm kiếm....");
                    setForeground(new Color(10, 10, 10, 120));
                }
            }
        });
    }

    private void initComponents(TableRowSorter<TableModel> sorter) {
        getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            public void update(DocumentEvent e) {
                String text = getText().trim();
                if (text.isEmpty() || text.equals("Tìm kiếm....")) {
                    sorter.setRowFilter(null);
                } else {
                    List<RowFilter<Object, Object>> filters = new ArrayList<>();
                    for (int i = 0; i < table.getColumnCount(); i++) {
                        filters.add(RowFilter.regexFilter("(?i)" + text, i));
                    }
                    sorter.setRowFilter(RowFilter.orFilter(filters));
                }
            }
        });
    }


}

