import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*; 

public class Aguile extends JFrame {
    public static final int SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;

    private FeaturePane pane;

    private FeatureManager fm;

    private FeatureRenderer focused;

    public Aguile(FeatureManager fm){
        super("Aguile");
        this.fm = fm;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new FeaturePane(fm);
        this.setContentPane(pane);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        pane.requestFocus();
    }

    public static void main(String[] args) throws IOException{
        FeatureManager fm = FeatureManager.getInstance();
        fm.readFromFile("test.json");
        //System.out.println(fm);
        Aguile aguile = new Aguile(fm);
        while(true){
            try{
                aguile.repaint();
                Thread.sleep(30);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
