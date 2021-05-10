import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileViewerPane extends JPanel implements MouseInputListener, MouseWheelListener{

    private static class Box{
        int x, y, width, height;
        boolean rendered;
        public Box(){
        }

        public void setCoor(int x, int y, int w, int h){
            this.x = x;
            this.y = y;
            this.width = w;
            this.height= h;
            render();
        }

        public void render(){
            rendered = true;
        }

        public void unrender(){
            rendered = false;
        }

        public boolean isIn(int x, int y){
            return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
        }
    }

    private static final int ICON_SIZE = 20, ICON_PADDING = 2;
    static {
        Image temp = null;
        try{
            temp = ImageIO.read(new File("folder.png"));
        }
        catch(IOException e){
            throw new FileViewingError("must have icon for folder called folder.png");
        }
        FOLDER_ICON = temp.getScaledInstance(ICON_SIZE-ICON_PADDING, ICON_SIZE-ICON_PADDING, Image.SCALE_DEFAULT);

        try{
            temp = ImageIO.read(new File("json.png"));
        }
        catch(IOException e){
            throw new FileViewingError("must have icon for json called json.png");
        }
        JSON_ICON = temp.getScaledInstance(ICON_SIZE-ICON_PADDING, ICON_SIZE-ICON_PADDING, Image.SCALE_DEFAULT);
    }

    private static final Image FOLDER_ICON, JSON_ICON;
    public static final int WIDTH = 640, HEIGHT = 360;   
    private static final int PADDING = 5;
    private static final int TEXT_HEIGHT = 20;

    private File dir;
    private int yOffset;
    private int dirSize;
    private String selected;
    private Path path;
    private HashMap<String, Box> boxes;

    public FileViewerPane(){
        path = Paths.get("").toAbsolutePath();
        dir = new File(path.toString());

        yOffset = 0;
        dirSize = 1;

        boxes = new HashMap<String, Box>();

        setMinimumSize(new Dimension(640, 360));
        setPreferredSize(new Dimension(640, 360));
        setMaximumSize(new Dimension(640, 360));
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    public void paint(Graphics g){
        super.paint(g);
        File[] files = dir.listFiles(f -> (f.isDirectory() && !f.getName().startsWith(".")) || (f.isFile() && f.getName().endsWith(".json")));
        if(files == null)
            dirSize = 0;
        else
            dirSize = files.length + 1;
        if(yOffset == 0){
            if(selected != null && selected.equals("..")){
                Color original = g.getColor();
                g.setColor(new Color(25, 25, 255, 255));
                g.fillRect(0, 0, WIDTH, TEXT_HEIGHT);
                g.setColor(original);
            }
            Box currBox = null;
            if(boxes.containsKey("..")){
                currBox = boxes.get("..");
            }
            else{
                currBox = new Box();
                boxes.put("..", currBox);
            }
            Color original = g.getColor();
            if(selected != null && selected.equals("..")){
                g.setColor(Color.white);
            }
            g.drawImage(FOLDER_ICON, ICON_PADDING, 0, null);
            PrettyDrawer.drawTextInArea(g, "..", 
                                            ICON_SIZE + PADDING, 0, 
                                            WIDTH - 10 - PADDING * 2, TEXT_HEIGHT - PADDING * 2 
                                    );
            if(selected != null && selected.equals("..")){
                g.setColor(original);
            }      
            currBox.setCoor(0, 0, WIDTH, TEXT_HEIGHT);
        }
        else{
            if(boxes.containsKey("..")){
                boxes.get("..").unrender();
            }
        }
        for (int i = 0;i<dirSize-1;i++){
            int yLoc = (i+1+yOffset)*(TEXT_HEIGHT);
            Box currBox = null;
            if(boxes.containsKey(files[i].getName())){
                currBox = boxes.get(files[i].getName());
            }
            else{
                currBox = new Box();
                boxes.put(files[i].getName(), currBox);
            }
            if(yLoc >= 0 && yLoc <= HEIGHT){
                if(files[i].getName().equals(selected)){
                    Color original = g.getColor();
                    g.setColor(new Color(25, 25, 255, 255));
                    g.fillRect(0, yLoc, WIDTH, TEXT_HEIGHT);
                    g.setColor(original);
                }
                if(files[i].isDirectory()){
                    g.drawImage(FOLDER_ICON, ICON_PADDING, yLoc, null);
                }
                else{
                    g.drawImage(JSON_ICON, ICON_PADDING, yLoc, null);
                }
                Color original = g.getColor();
                if(files[i].getName().equals(selected)){
                    g.setColor(Color.white);
                }
                PrettyDrawer.drawTextInArea(g, files[i].getName(), 
                                            ICON_SIZE + PADDING, yLoc, 
                                            WIDTH - 10 - PADDING * 2, TEXT_HEIGHT - PADDING * 2 
                                           );
                if(files[i].getName().equals(selected)){
                    g.setColor(original);
                }      
                currBox.setCoor(0, yLoc, WIDTH, TEXT_HEIGHT);
            }
            else{
                currBox.unrender();
            }
        }
        
    }

    public void createFile(String fname, String data) throws IOException{
        Path toPath = path.resolve(fname);
        File toCreate = new File(toPath.toString());
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(toCreate)));
        out.println(data);
        out.close();
    }

    public File getSelected(){
        if(selected == null) return null;
        Path toPath = path.resolve(selected);
        File ret = new File(toPath.toString());
        return ret;
    }

    public void mouseClicked(MouseEvent e){
        boolean reset = false;
        for(String fName: boxes.keySet()){
            Box currBox = boxes.get(fName);
            if(e.getClickCount() == 1){
                if(currBox.rendered && currBox.isIn(e.getX(), e.getY())){
                    selected = fName;
                }
            }
            if(e.getClickCount() == 2){
                if(currBox.rendered && currBox.isIn(e.getX(), e.getY()) && fName.equals(selected)){
                    if(selected.equals("..")){
                        path = path.getRoot().resolve(path.subpath(0, path.getNameCount()-1));
                        dir = new File(path.toString());
                    }
                    else{
                        path = path.resolve(selected);
                        dir = new File(path.toString());
                    }
                    reset = true;
                    yOffset = 0;
                    break;
                }
            }
        }
        if(reset){
            boxes = new HashMap<String, Box>();
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
        if(dirSize > HEIGHT / TEXT_HEIGHT){
            if(notches < 0) { //scroll up, page moves down
                yOffset = Math.min(0, yOffset - notches );
            }
            else{ // scroll down, page moves up
                yOffset = Math.max(yOffset - notches, HEIGHT / TEXT_HEIGHT - dirSize);
            }
        }
    }
}
