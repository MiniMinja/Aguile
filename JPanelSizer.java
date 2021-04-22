import javax.swing.*;
import java.awt.Dimension;
public class JPanelSizer {
    public static void setSize(JPanel panel, int width, int height){
        panel.setPreferredSize(new Dimension(width, height));
        panel.setMinimumSize(new Dimension(width, height));
        panel.setMaximumSize(new Dimension(width, height));
    }
}
