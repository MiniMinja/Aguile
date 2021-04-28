import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class FeaturePane extends JPanel implements MouseInputListener, MouseWheelListener{

    public static final int WIDTH = 1280, HEIGHT = 300;
    public static final int LEFT_WEIGHT = 1, MIDDLE_WEIGHT = 4, RIGHT_WEIGHT = 1;
    public static final int PADDING = 10;
    public static final int EXTENDED_HEIGHT = 50;
    public static final int BUTT_WIDTH = 100, BUTT_HEIGHT = 100;
    public static final int EDIT_WIDTH = 100, EDIT_HEIGHT = 30;
    public static final Color bg = new Color(255, 255, 255, 255), 
                              elmt_bg = new Color(0x42, 0xA5, 0xF5, 255),
                              elmt_fore = new Color(0x00, 0x77, 0xC2, 255),
                              txt = new Color(0x00, 0x00, 0x00, 255),
                              elmt_bg2 = new Color(0x15, 0x65, 0xC0, 255),
                              elmt_fore2 = new Color(0x5E, 0x92, 0xF3, 255),
                              txt2 = new Color(0xFF, 0xFF, 0xFF, 255);


    /*-------------------------------------------------------------------------------------
        Instance Part
    ---------------------------------------------------------------------------------------
    */

    private FeatureManager fm;
    private int yOffset, lowestBar;
    private Feature focused;
    private int[] editBox, removeBox;

    private ArrayList<int[]> featureBoxes;
    private ArrayList<Integer> registry;

    public FeaturePane(FeatureManager fm){
        this.fm = fm;
        yOffset = 0;
        lowestBar = 0;
        featureBoxes = new ArrayList<int[]>();
        registry = new ArrayList<Integer>();
        focused = null;
        editBox = new int[4];
        removeBox = new int[4];
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.setPreferredSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.setMinimumSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.setMaximumSize(new Dimension(Aguile.SCREEN_WIDTH, Aguile.SCREEN_HEIGHT));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    public int totalFeatureHeight(){
        int extended = 0;
        if(focused != null) extended = EXTENDED_HEIGHT;
        return fm.getSize() * (HEIGHT + 10) + extended;
    }

    public void background(Graphics g){
        Color original = g.getColor();
        g.setColor(bg);
        g.fillRect(0, 0, WIDTH, Aguile.SCREEN_HEIGHT);
        g.setColor(original);
    }

    public void renderNextFeature(Graphics g, Feature f){
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        int leftWidth = WIDTH * LEFT_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        int middleWidth = WIDTH * MIDDLE_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        int twoThirds = leftWidth + middleWidth;
        int totalOffset = yOffset + lowestBar;
        //System.out.println("Feature Order: "+ featureOrder);
        //System.out.println("Feature Offset: "+featureOffset);
        //System.out.println("yOffset: "+yOffset);

        int index = registry.indexOf(f.id());
        if(index != -1){
            int[] box = featureBoxes.get(index);
            box[1] = PADDING+totalOffset;
        }else{
            int[] box = new int[]{PADDING, PADDING+totalOffset, WIDTH - PADDING * 2, HEIGHT - PADDING * 2};
            registry.add(f.id());
            featureBoxes.add(box);
        }

        Color original = g.getColor();
        g.setColor(elmt_bg);
        g.fillRect(PADDING, PADDING + totalOffset, WIDTH - PADDING * 2, HEIGHT- PADDING * 2 );
        lowestBar += HEIGHT;
        if(f == focused){
            g.setColor(elmt_bg);
            g.fillRect(PADDING, totalOffset + HEIGHT - PADDING, WIDTH - PADDING * 2, EXTENDED_HEIGHT);
            /*
            g.setColor(elmt_fore);
            for(int i = -2;i<=2;i++){
                g.drawRect(PADDING + i, 
                            totalOffset + HEIGHT - PADDING + i, 
                            WIDTH - PADDING * 2 - i * 2, 
                            EXTENDED_HEIGHT - i * 2);
            }
            */
            lowestBar += EXTENDED_HEIGHT;
        }
        g.setColor(elmt_fore);
        for(int i = -2;i<=2;i++){
            //g.drawRect(PADDING + i, PADDING + totalOffset + i, WIDTH - PADDING * 2-i*2, HEIGHT- PADDING * 2-i*2);
            //g.drawLine(leftWidth+i, PADDING + totalOffset, leftWidth+i, HEIGHT+totalOffset - PADDING );
            //g.drawLine(twoThirds+i, PADDING + totalOffset, twoThirds+i, HEIGHT+totalOffset - PADDING );
        }
        g.setColor(txt);
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
        
        if(f == focused){
            int thirdMark = WIDTH / 3;
            int thirdMark2 = WIDTH / 3 * 2;
            int start1X = thirdMark - EDIT_WIDTH / 2;
            int start2X = thirdMark2 - EDIT_WIDTH / 2;
            int startY = totalOffset + HEIGHT + EXTENDED_HEIGHT / 2 - EDIT_HEIGHT / 2 - PADDING;
            editBox[0] = start1X;
            editBox[1] = startY;
            editBox[2] = EDIT_WIDTH;
            editBox[3] = EDIT_HEIGHT;
            removeBox[0] = start2X;
            removeBox[1] = startY;
            removeBox[2] = EDIT_WIDTH;
            removeBox[3] = EDIT_HEIGHT;
            g.setColor(elmt_bg2);
            g.fillRect(start1X,
                        startY,
                        EDIT_WIDTH,
                        EDIT_HEIGHT);
            g.fillRect(start2X,
                        startY,
                        EDIT_WIDTH,
                        EDIT_HEIGHT);
            g.setColor(elmt_fore2);
            /*
            for(int i = -2;i<=2;i++){
                g.drawRect(start1X + i,
                            startY + i,
                            EDIT_WIDTH - i * 2,
                            EDIT_HEIGHT - i * 2);
                
                g.drawRect(start2X + i,
                            startY + i,
                            EDIT_WIDTH - i * 2,
                            EDIT_HEIGHT - i * 2);

                g.drawRect(start1X + i,
                            startY + i,
                            EDIT_WIDTH - i * 2,
                            EDIT_HEIGHT - i * 2);
                
                g.drawRect(start2X + i,
                            startY + i,
                            EDIT_WIDTH - i * 2,
                            EDIT_HEIGHT - i * 2);
            }
            */
            g.setColor(txt2);
            
            PrettyDrawer.drawTextInMiddleOfArea(g, "EDIT", 
                                                    start1X,
                                                    startY,
                                                    EDIT_WIDTH,
                                                    EDIT_HEIGHT);
            
            PrettyDrawer.drawTextInMiddleOfArea(g, "REMOVE", 
                                                    start2X,
                                                    startY,
                                                    EDIT_WIDTH,
                                                    EDIT_HEIGHT);
        }
                                                
        g.setColor(original);
    }

    public int[] buttonBox(){
        int startY = totalFeatureHeight() + yOffset;
        int startX = Aguile.SCREEN_WIDTH / 2 - BUTT_WIDTH/2;
        return new int[]{startX, startY, BUTT_WIDTH, BUTT_HEIGHT};
    }

    public void renderAddButton(Graphics g){
        Color original = g.getColor();
        int[] buttonArea = buttonBox();
        int startX = buttonArea[0];
        int startY = buttonArea[1];
        g.setColor(elmt_bg);
        g.fillRect(startX + PADDING, startY + PADDING, BUTT_WIDTH - PADDING * 2, BUTT_HEIGHT - PADDING * 2);
        g.setColor(elmt_fore);
        for(int i = -2;i<=2;i++){
            g.drawRect(startX + i + PADDING, 
                        startY + i + PADDING, 
                        BUTT_WIDTH - i * 2 - PADDING * 2, 
                        BUTT_HEIGHT - i * 2 - PADDING * 2);
        }
        int centerX = startX + BUTT_WIDTH / 2;
        int centerY = startY + BUTT_HEIGHT / 2;
        for(int i = -10;i<=10;i++){
            g.drawLine(centerX + i, 
                        centerY - (BUTT_HEIGHT / 2 - PADDING - 10), 
                        centerX + i, 
                        centerY + (BUTT_HEIGHT / 2 - PADDING- 10));
            g.drawLine(centerX - (BUTT_WIDTH / 2 - PADDING - 10), 
                        centerY + i, 
                        centerX + (BUTT_WIDTH / 2 - PADDING - 10), 
                        centerY + i);
        }
        g.setColor(original);
    }

    public void unrender(Integer id){
        int index = registry.indexOf(id);
        registry.remove(index);
        featureBoxes.remove(index);
    }

    public void paint(Graphics g){
        super.paint(g);
        background(g);
        lowestBar = 0;
        fm.render(g);
        renderAddButton(g);
    }

    public void mouseClicked(MouseEvent e){
        /*
        System.out.println(registry);
        for(int[] boxes: featureBoxes){
            System.out.print(Arrays.toString(boxes)+" ");
        }
        System.out.println();
        */
        int[] buttonBox = buttonBox();
        if(buttonBox[0] <= e.getX() && e.getX() <= buttonBox[0] + buttonBox[2] &&
            buttonBox[1] <= e.getY() && e.getY() <= buttonBox[1] + buttonBox[3]){
                fm.createFeature();
        }
        else if(editBox[0] <= e.getX() && e.getX() <= editBox[0] + editBox[2] &&
            editBox[1] <= e.getY() && e.getY() <= editBox[1] + editBox[3]){
                if(focused != null){
                    unrender(focused.id());
                    fm.editFeature(focused.id());
                    focused = null;
                }
        }
        else if(removeBox[0] <= e.getX() && e.getX() <= removeBox[0] + removeBox[2] &&
                removeBox[1] <= e.getY() && e.getY() <= removeBox[1] + removeBox[3]){
                    if(focused != null){
                        unrender(focused.id());
                        fm.removeFeature(focused.id());
                        focused = null;
                    }
        }
        else{
            for(int index = 0;index<featureBoxes.size();index++){
                int[] area = featureBoxes.get(index);
                if(area[0] <= e.getX() && e.getX() <= area[0] + area[2] &&
                    area[1] <= e.getY() && e.getY() <= area[1] + area[3]){
                        focused = fm.getFeature(registry.get(index));
                }
            }
        }
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
        int maxHeight = totalFeatureHeight();
        if(maxHeight + BUTT_HEIGHT >= Aguile.SCREEN_HEIGHT){
            if(notches < 0) { //scroll up
                yOffset = Math.min(0, yOffset - notches  * 10);
            }
            else{ // scroll down
                yOffset = Math.max(yOffset - notches * 10, -maxHeight - BUTT_HEIGHT + Aguile.SCREEN_HEIGHT);
            }
        }
    }
}
