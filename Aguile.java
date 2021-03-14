import javax.swing.*;
import java.awt.*;

public class Aguile extends JFrame {

    private FeatureManager fm;

    private JMenuBar menubar;
    private JMenu fileMenu;
    private JMenuItem load, save;

    private JScrollPane mainPane;
    
    public Aguile(){
        super("Aguile");
        fm = new FeatureManager();

        menubar = new JMenuBar();{
            fileMenu = new JMenu("File");{
                load = new JMenuItem("Load...");
                save = new JMenuItem("Save...");
                fileMenu.add(load);
                fileMenu.add(save);
            }
            menubar.add(fileMenu);
        }
        this.setJMenuBar(menubar);

        mainPane = new JScrollPane();{
            mainPane.setMinimumSize(new Dimension(1280, 720));
            mainPane.setPreferredSize(new Dimension(1270, 720));
            mainPane.setMaximumSize(new Dimension(1270, 720));
        }
        this.setContentPane(mainPane);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args){
        Aguile aguile = new Aguile();
    }

}
