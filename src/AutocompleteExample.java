import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteExample extends JTextField {
    private List<String> stringList  ;
    private JPopupMenu popupMenu = new JPopupMenu();
    // Constructor
    public AutocompleteExample(List<String> stringList) {
        super();
        initComponents();
        this.stringList = stringList;
    }

    // Danh sách gợi ý
    private static List<String> getSuggestions(String input, List<String> items) {
        List<String> suggestions = new ArrayList<>();
        for (String item : items) {
            if(item.toLowerCase().contains(input.toLowerCase())) {
                suggestions.add(item);
            }
        }
        return suggestions;
    }

    // Gắn sự kiện
    private void initComponents(){
         this.getDocument().addDocumentListener(new SimpleDocumentListener() {
             @Override
             public void update(DocumentEvent e) {
                 updateAction();
             }
        });
    }
    // Update danh sách
    private void updateAction(){
         String input = this.getText();
         if(input.isEmpty()){
             this.popupMenu.setVisible(false);
             return;
         }

         popupMenu.removeAll();

         for(String suggestion : getSuggestions(input, stringList)) {
             JMenuItem item = new JMenuItem(suggestion);
             item.addActionListener(new ActionListener() {
                 @Override
                 public void actionPerformed(ActionEvent e) {
                     setText(suggestion);
                     popupMenu.setVisible(false);
                 }
             });
             popupMenu.add(item);
         };

         if(popupMenu.getComponentCount() > 0){
             popupMenu.show(this,0,getHeight());
             popupMenu.setVisible(true);
         }else {
             popupMenu.setVisible(false);
         }
    }
}
