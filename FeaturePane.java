import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class FeaturePane extends JPanel implements MouseInputListener, MouseWheelListener{

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

    public void paint(Graphics g){
        super.paint(g);
        fm.render(g, yOffset);
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
        if(notches < 0) {
            yOffset = Math.max(0, yOffset + notches);
        }
        else{
            yOffset += notches;
        }
    }
}
