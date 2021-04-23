
import java.awt.*;

public class FeatureRenderer{

    public static final int WIDTH = 1280, HEIGHT = 200;
    public static final int LEFT_WEIGHT = 1, MIDDLE_WEIGHT = 4, RIGHT_WEIGHT = 1;
    public static final int PADDING = 10;

    public static void renderAtYOffset(Graphics g, Feature f, int yOffset){
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        int leftWidth = WIDTH * LEFT_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        int middleWidth = WIDTH * MIDDLE_WEIGHT / (LEFT_WEIGHT+MIDDLE_WEIGHT+RIGHT_WEIGHT);
        g.drawLine(leftWidth, 0+ yOffset, leftWidth, HEIGHT+ yOffset);
        g.drawLine(leftWidth+middleWidth, 0+ yOffset, leftWidth+middleWidth, HEIGHT+ yOffset);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Name: \n"+ f.fName(), 
                                                PADDING, 
                                                PADDING + yOffset, 
                                                leftWidth-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "ID: \n"+f.id(), 
                                                PADDING, 
                                                PADDING + HEIGHT/2+ yOffset, 
                                                leftWidth-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        String asthe = "As the : " + f.asthe();
        String iwant = "I want : " + f.iwant();
        String sothat = "So that : " + f.sothat();
        PrettyDrawer.drawTextInMiddleOfArea(g, asthe+"\n"+iwant+"\n"+sothat, 
                                                PADDING + leftWidth, 
                                                PADDING+ yOffset, 
                                                middleWidth-PADDING*2, 
                                                HEIGHT - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Size: \n"+ f.size(), 
                                                PADDING + leftWidth + middleWidth, 
                                                PADDING+ yOffset, 
                                                WIDTH-leftWidth-middleWidth-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
        PrettyDrawer.drawTextInMiddleOfArea(g, "Implemented: \n"+f.implemented(), 
                                                PADDING + leftWidth + middleWidth, 
                                                PADDING + HEIGHT/2+ yOffset, 
                                                WIDTH-leftWidth-middleWidth-PADDING*2, 
                                                HEIGHT/2 - PADDING*2);
    }
    
}   
