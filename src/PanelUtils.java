import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

class LabelUtils {
    public static JLabel createLabel(String text, int fontSize, Color foreground) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.BOLD, fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setForeground(foreground);
        return label;
    }
}


class PanelUtils {
    public static JPanel createRoundedPanelWithTextField(int radius, int x, int y, Font font, JTextField textField) {
        JPanel panel = new RoundedPanel(radius);
        panel.setBounds(x, y, 190, 30);
        panel.setLayout(new BorderLayout());

        textField.setFont(font);
        textField.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        textField.setOpaque(false);

        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }
    public static JPanel createRoundedPanelWithTextField(int radius, int x, int y,int width, int height, Font font, JTextField textField) {
        JPanel panel = new RoundedPanel(radius);
        panel.setBounds(x, y, width, height);
        panel.setLayout(new BorderLayout());

        textField.setFont(font);
        textField.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
        textField.setOpaque(false);

        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }

    public static JPanel createRoundedPanelWithLabel(String text, int fontSize, int width, int height, Color color) {
        JPanel panel = new RoundedPanel(10);
        JLabel label = LabelUtils.createLabel(text, fontSize, Color.WHITE);
        panel.setBounds(width, height, 100, 30);
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.setBackground(color);
        return panel;
    }

}
