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
    public static FileViewer start(String mode, String data){
        Flags.setState(Flags.FILE_VIEWING);
        instance = new FileViewer(mode, data);
        instance.setVisible(true);
        return instance;
    }

    public static void render(){
        instance.filepane.repaint();
    }

    private int mode;
    private String toWrite;

    private JPanel contentPane;
    private JTextField fileName;
    private JButton save, open;
    private FileViewerPane filepane; 

    private Thread repaintJob;
    private boolean warningToClose;
    private boolean closing;

    private FileViewer(String mode, String data){
        super("FileViewer");
        if(mode.equals("r")){
            this.mode = READ;
        }
        else if(mode.equals("w")){
            this.mode = WRITE;
            toWrite = data;
        }
        else{
            throw new FileViewingError("that mode does not exist");
        }

        contentPane = buildContent();

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                Flags.setState(Flags.IDLE);
                closing = true;
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(contentPane);
        pack();

        warningToClose = false;
        closing = false;

        repaintJob = new Thread(new Runnable(){
            public void run(){
                while(!closing){
                    try{
                        if(!Flags.isError() && Flags.getState() == Flags.FILE_VIEWING){
                            filepane.repaint();
                            if(warningToClose){
                                if(ErrorMindow.retVal == ErrorMindow.CONFIRMATION){
                                    filepane.checkQueue();
                                    FileViewer.this.dispatchEvent(new WindowEvent(FileViewer.this, WindowEvent.WINDOW_CLOSING));
                                }
                                else if(ErrorMindow.retVal == ErrorMindow.CANCEL){
                                    filepane.ignoreLastQueue();
                                }
                            }
                        }
                        Thread.sleep(100);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        repaintJob.start();
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
                    if(!Flags.isError() && Flags.getState() == Flags.FILE_VIEWING){
                        String fname = fileName.getText();
                        if(fname.length() > 0){
                            if(!fname.endsWith(".json"))
                                fname += ".json";
                            try{
                                filepane.checkWriteFile(fname, toWrite);
                            }catch(IOException err){
                                System.out.println("File could not be created");
                            }
                            closeWhenConfirm();
                        }
                        else{
                            System.out.println("please enter a filename");
                        }
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
                    if(!Flags.isError() && Flags.getState() == Flags.FILE_VIEWING){
                        File toOpen = filepane.getSelected();
                        if(toOpen != null && toOpen.getName().endsWith(".json")){
                            System.out.println(toOpen.toPath().toString());
                            closeWhenConfirm();
                        }
                        else{
                            System.out.println("select a JSON file");
                        }
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

    public void closeWhenConfirm(){
        warningToClose = true;
    }
}
