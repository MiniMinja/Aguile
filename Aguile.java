import javax.swing.*;
import java.awt.*;

public class Aguile extends JFrame {

    private static final Dimension windowSize = new Dimension(1280, 720);
    private static final Dimension labelSize = new Dimension(1280, 25);
    private static final String[] lblStrings = {"ID", "Description", "Size", "Completed"};
    private static final double[] defaultPercentages = {.1, .6, .2, .1};

    public static int calculateFillerSpace(int screenWidth, int labelWidth, double percentage){
        if(labelWidth > percentage * screenWidth){
            System.out.println("ERROR: LABEL IS TOO WIDE!!!");
        }
        else if(labelWidth == (int)(percentage * screenWidth)){
            System.out.println("Perfect match");
        }
        double remainder = screenWidth * percentage - labelWidth;
        //System.out.println(String.format("Screen Width: %d Label Width: %d Percentage: %.2f", screenWidth, labelWidth, percentage));
        return (int)remainder/2;
    }

    private FeatureManager fm;

    private JMenuBar menubar;
    private JMenu fileMenu;
    private JMenuItem load, save;

    private JPanel mainPane;
    private JLabel lbl_id, lbl_desc, lbl_size, lbl_state;
    private Box.Filler idFiller1, idFiller2, descFiller1, descFiller2, sizeFiller1, sizeFiller2, stateFiller1, stateFiller2;
    private boolean allocated;

    private JPanel divider;

    private JScrollPane contentPane;
    
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

        mainPane = new JPanel();{
            BoxLayout layout = new BoxLayout(mainPane, BoxLayout.Y_AXIS);
            mainPane.setLayout(layout);
            mainPane.setMinimumSize(windowSize);
            mainPane.setPreferredSize(windowSize);
            mainPane.setMaximumSize(windowSize);

            divider = new JPanel();{
                divider.setLayout(new BoxLayout(divider, BoxLayout.X_AXIS));
                divider.setMinimumSize(labelSize);
                divider.setPreferredSize(labelSize);
                divider.setMaximumSize(labelSize);

                lbl_id=new JLabel(lblStrings[0], JLabel.CENTER);
                lbl_desc=new JLabel(lblStrings[1], JLabel.CENTER);
                lbl_size=new JLabel(lblStrings[2], JLabel.CENTER);
                lbl_state=new JLabel(lblStrings[3], JLabel.CENTER);

                divider.add(lbl_id);
                divider.add(lbl_desc);
                divider.add(lbl_size);
                divider.add(lbl_state);
                allocated = false;
            }
            mainPane.add(divider);
        }
        this.setContentPane(mainPane);

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        super.paint(g);
        if(!allocated){
        
            Dimension idFillerSpace = new Dimension(
                calculateFillerSpace(windowSize.width, lbl_id.getWidth(), defaultPercentages[0]), 
                25
            );
            System.out.println(idFillerSpace);
            idFiller1 = new Box.Filler(idFillerSpace, idFillerSpace, idFillerSpace);
            idFiller2 = new Box.Filler(idFillerSpace, idFillerSpace, idFillerSpace);

            Dimension descFillerSpace = new Dimension(
                calculateFillerSpace(windowSize.width, lbl_desc.getWidth(), defaultPercentages[1]), 
                25
            );
            System.out.println(descFillerSpace);
            descFiller1= new Box.Filler(descFillerSpace, descFillerSpace, descFillerSpace);
            descFiller2= new Box.Filler(descFillerSpace, descFillerSpace, descFillerSpace);

            Dimension sizeFillerSpace = new Dimension(
                calculateFillerSpace(windowSize.width, lbl_size.getWidth(), defaultPercentages[2]), 
                25
            );
            System.out.println(sizeFillerSpace);
            sizeFiller1= new Box.Filler(sizeFillerSpace, sizeFillerSpace, sizeFillerSpace);
            sizeFiller2= new Box.Filler(sizeFillerSpace, sizeFillerSpace, sizeFillerSpace);

            Dimension stateFillerSpace = new Dimension(
                calculateFillerSpace(windowSize.width, lbl_state.getWidth(), defaultPercentages[3]), 
                25
            );
            System.out.println(stateFillerSpace);
            stateFiller1= new Box.Filler(stateFillerSpace, stateFillerSpace, stateFillerSpace);
            stateFiller2= new Box.Filler(stateFillerSpace, stateFillerSpace, stateFillerSpace);
            
            divider.remove(lbl_id);
            divider.remove(lbl_desc);
            divider.remove(lbl_size);
            divider.remove(lbl_state);

            divider.add(idFiller1);
            divider.add(lbl_id);
            divider.add(idFiller2);
            divider.add(descFiller1);
            divider.add(lbl_desc);
            divider.add(descFiller2);
            divider.add(sizeFiller1);
            divider.add(lbl_size);
            divider.add(sizeFiller2);
            divider.add(stateFiller1);
            divider.add(lbl_state);
            divider.add(stateFiller2);
            allocated = true;
        }
        
    }

    public static void main(String[] args){
        Aguile aguile = new Aguile();
    }

}
