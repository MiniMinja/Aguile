import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ErrorMindow extends JFrame implements ActionListener{

    public static final int CONFIRMATION = 0, CANCEL = 1;
    public static int retVal = -1;

    public static final int WIDTH = 480, HEIGHT = 280;
    public static final int MSG_HEIGHT = 200, PADDING = 20;
    public static final int ERROR = 2, WARNING = 1;

    private static Thread errorJob;

    public static void Error(String msg){
        retVal = -1;
        ErrorMindow ret = new ErrorMindow(ERROR, msg);
        Flags.setError(true);
        while(!ret.ended()){
            try{
                Thread.sleep(100);
                ret.errorMessage.repaint();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void Warning(String msg){
        retVal = -1;
        ErrorMindow ret = new ErrorMindow(WARNING, msg);
        Flags.setError(true);
        errorJob = new Thread(new Runnable(){
            public void run(){
                while(!ret.ended){
                    try{
                        Thread.sleep(100);
                    }catch(InterruptedException ie){
                        ie.printStackTrace();
                    }
                }
            }
        });
        errorJob.start();
    }

    private int mode;
    private JPanel errorpane;
    private JPanel errorMessage;
    private JPanel btnContainer;
    private JButton ok, yes, cancel;

    private String msg;

    private boolean ended;

    private ErrorMindow(int m, String msg){
        if(m == WARNING){
            setTitle("WARNING");
        }
        else{
            setTitle("ERROR");
        }
        this.mode = m;
        this.msg = msg;
        errorpane = build();
        setContentPane(errorpane);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if(retVal == -1) retVal = CANCEL;
                Flags.setError(false);
                ended = true;
            }
        });
        setVisible(true);
        ended = false;
    }

    public JPanel build(){
        JPanel ret = new JPanel();
        BoxLayout layout = new BoxLayout(ret, BoxLayout.Y_AXIS);
        ret.setLayout(layout);
        ret.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        ret.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        ret.setMaximumSize(new Dimension(WIDTH, HEIGHT));

        errorMessage = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                Font original = g.getFont();
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
                PrettyDrawer.drawTextInMiddleOfArea(g, msg, 0, 20, ErrorMindow.WIDTH, MSG_HEIGHT - 40);
                g.setFont(original);
            }
        };
        errorMessage.setMinimumSize(new Dimension(WIDTH, MSG_HEIGHT));
        errorMessage.setPreferredSize(new Dimension(WIDTH, MSG_HEIGHT));
        errorMessage.setMaximumSize(new Dimension(WIDTH, MSG_HEIGHT));
        ret.add(errorMessage);

        btnContainer = new JPanel();
        btnContainer.setMinimumSize(new Dimension(WIDTH, HEIGHT - MSG_HEIGHT));
        btnContainer.setPreferredSize(new Dimension(WIDTH, HEIGHT - MSG_HEIGHT));
        btnContainer.setMaximumSize(new Dimension(WIDTH, HEIGHT - MSG_HEIGHT));
        FlowLayout btnLayout = new FlowLayout(FlowLayout.CENTER, 30, 30);
        btnContainer.setLayout(btnLayout);
        if(mode == ERROR){
            ok = new JButton("OK");
            ok.setActionCommand("ok");
            ok.addActionListener(this);
            btnContainer.add(ok);
        }
        else{
            yes = new JButton("Yes");
            yes.setActionCommand("yes");
            cancel = new JButton("Cancel");
            cancel.setActionCommand("cancel");
            yes.addActionListener(this);
            cancel.addActionListener(this);
            btnContainer.add(yes);
            btnContainer.add(cancel);
        }
        ret.add(btnContainer);

        return ret;
    }

    public boolean ended(){
        return ended;
    }

    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        if(cmd.equals("yes") || cmd.equals("ok")){
            retVal = CONFIRMATION;
        }
        else{
            retVal = CANCEL;
        }
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
