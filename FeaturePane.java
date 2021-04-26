import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class FeaturePane extends JPanel implements MouseInputListener, MouseWheelListener{

    public static final int WIDTH = 1280, HEIGHT = 200;
    public static final int LEFT_WEIGHT = 1, MIDDLE_WEIGHT = 4, RIGHT_WEIGHT = 1;
    public static final int PADDING = 10;
    /*-------------------------------------------------------------------------------------
        Instance Part
    ---------------------------------------------------------------------------------------
    */

    private FeatureManager fm;
    private int yOffset;

    public FeaturePane(FeatureManager fm){
        this.fm = fm;
        yOffset = 0;
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.setMinimumSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.setMaximumSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    public void renderAtYOffset(Graphics g, Feature f, int featureOrder){
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        int leftWidth = WIDTH * LEFT_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        int middleWidth = WIDTH * MIDDLE_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        int twoThirds = leftWidth + middleWidth;
        int featureOffset = (HEIGHT+10) * featureOrder;
        int totalOffset = yOffset + featureOffset;
        //System.out.println("Feature Order: "+ featureOrder);
        //System.out.println("Feature Offset: "+featureOffset);
        //System.out.println("yOffset: "+yOffset);
        g.drawLine(leftWidth, totalOffset, leftWidth, HEIGHT+totalOffset);
        g.drawLine(twoThirds, totalOffset, twoThirds, HEIGHT+ totalOffset);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Name: \n"+ f.fName(), 
                                                PADDING, 
                                                PADDING + totalOffset, 
                                                leftWidth - PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "ID: \n"+f.id(), 
                                                PADDING, 
                                                PADDING + HEIGHT/2+ totalOffset, 
                                                leftWidth-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        String asthe = "As the : " + f.asthe();
        String iwant = "I want : " + f.iwant();
        String sothat = "So that : " + f.sothat();
        PrettyDrawer.drawTextInMiddleOfArea(g, asthe+"\n"+iwant+"\n"+sothat, 
                                                PADDING + leftWidth, 
                                                PADDING+ totalOffset, 
                                                middleWidth-PADDING*2, 
                                                HEIGHT - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Size: \n"+ f.size(), 
                                                PADDING + twoThirds, 
                                                PADDING+ totalOffset, 
                                                WIDTH-twoThirds-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Implemented: \n"+f.implemented(), 
                                                PADDING + twoThirds, 
                                                PADDING + HEIGHT/2+ totalOffset, 
                                                WIDTH-twoThirds-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
    }

    public void paint(Graphics g){
        super.paint(g);
        fm.render(g);
    }

    public void mouseClicked(MouseEvent e){
        
    }

    public void mousePressed(MouseEvent e){

    }

    public void mouseReleased(MouseEvent e){

    }

    public void mouseEntered(MouseEvent e){

    }

    public void mouseExited(MouseEvent e){

    }

    public void mouseDragged(MouseEvent e){

    }

    public void mouseMoved(MouseEvent e){

    }

    public void mouseWheelMoved(MouseWheelEvent e){
        int notches = e.getWheelRotation();
        if(notches < 0) { //scroll up
            yOffset = Math.min(0, yOffset - notches  * 10);
        }
        else{ // scroll down
            int maxHeight = fm.getSize() * (HEIGHT + 10);
            yOffset = Math.max(yOffset - notches * 10, -maxHeight + Aguile.SCREEN_HEIGHT);
        }
    }
}
