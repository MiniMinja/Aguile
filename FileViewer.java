import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileViewer extends JFrame{
    private static final int READ = 1;
    private static final int WRITE = 2;

    public static final int WIDTH = 640, 
                            BOTTOM_HEIGHT = 50, 
                            HEIGHT = BOTTOM_HEIGHT+FileViewerPane.HEIGHT + 40, 
                            PADDING = 10; 

    private static FileViewer instance;
    public static FileViewer start(String mode){
        instance = new FileViewer(mode);
        instance.setVisible(true);
        return instance;
    }

    public static void render(){
        instance.filepane.repaint();
    }

    private int mode;
    private JPanel contentPane;
    private JTextField fileName;
    private JButton save, open;
    private FileViewerPane filepane; 

    private FileViewer(String mode){
        super("FileViewer");
        if(mode.equals("r")){
            this.mode = READ;
        }
        else if(mode.equals("w")){
            this.mode = WRITE;
        }
        else{
            throw new FileViewingError("that mode does not exist");
        }

        contentPane = buildContent();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();
    }

    public JPanel buildContent(){
        filepane = new FileViewerPane();

        JPanel padding = new JPanel();
        padding.setMinimumSize(new Dimension(640, BOTTOM_HEIGHT));
        padding.setPreferredSize(new Dimension(640, BOTTOM_HEIGHT));
        padding.setMaximumSize(new Dimension(640, BOTTOM_HEIGHT));

        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new BorderLayout());
        bottomBar.setMinimumSize(new Dimension(640-PADDING * 2, BOTTOM_HEIGHT - PADDING * 2));
        bottomBar.setPreferredSize(new Dimension(640-PADDING * 2, BOTTOM_HEIGHT - PADDING * 2));
        bottomBar.setMaximumSize(new Dimension(640-PADDING * 2, BOTTOM_HEIGHT - PADDING * 2));

        if(mode == WRITE){
            fileName = new JTextField(20);
            save = new JButton("SAVE");
            save.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    String fname = fileName.getText();
                    if(fname.length() > 0){
                        if(!fname.endsWith(".json"))
                            fname += ".json";
                        try{
                            filepane.createFile(fname, "test!!");
                        }catch(IOException err){
                            System.out.println("File could not be created");
                        }
                        dispose();
                    }
                    else{
                        System.out.println("please enter a filename");
                    }
                }
            });
            bottomBar.add(fileName, BorderLayout.WEST);
            bottomBar.add(save, BorderLayout.EAST);
        }
        else{
            open = new JButton("OPEN");
            open.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e){
                    File toOpen = filepane.getSelected();
                    if(toOpen != null){
                        System.out.println(toOpen.toPath().toString());
                        dispose();
                    }
                    else{
                        System.out.println("select a JSON file");
                    }
                }
            });
            bottomBar.add(open, BorderLayout.EAST);
        }
        padding.add(bottomBar);

        JPanel ret = new JPanel();
        BoxLayout layout = new BoxLayout(ret, BoxLayout.Y_AXIS);
        ret.setLayout(layout);
        ret.add(filepane);
        ret.add(padding);
        return ret;
    }
}
