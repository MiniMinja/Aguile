import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Graphics;
import java.awt.Dimension;

public class FeatureBuilderMindow extends JFrame{

    public static FeatureBuilderMindow instance;

    public static void start(int id){
        Flags.setState(Flags.EDITING_FEATURE);
        instance = new FeatureBuilderMindow(id);
    }

    public static RadioButtonListener listener = new RadioButtonListener();

    private static class RadioButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent e){
            if(e.getActionCommand().equals("create")){
                Feature.Builder builder = new Feature.Builder();
                if(instance.tf_name.getText().length() == 0){
                    throw new FeatureManagingError("cannot create a feature without a name");
                }
                builder.setFName(instance.tf_name.getText());
                builder.setId(instance.id);
                String asthe = instance.tf_asthe.getText();
                String iwant = instance.tf_iwant.getText();
                String sothat = instance.tf_sothat.getText();
                if(asthe.length() == 0 || iwant.length() == 0 || sothat.length() == 0){
                    throw new FeatureManagingError("cannot create a feature with empty description");
                }
                builder.setDesc(asthe, iwant, sothat);
                if(instance.selectedSize == null){
                    throw new FeatureManagingError("cannot create feature without selecting a size");
                }
                builder.setSize(instance.selectedSize);
                builder.setImplemented(false);
                FeatureManager.getInstance().addFeature(builder.build());
                instance.dispatchEvent(new WindowEvent(instance, WindowEvent.WINDOW_CLOSING));
            }
            else if(e.getActionCommand().equals("small")){
                instance.selectedSize = "SMALL";
            }
            else if(e.getActionCommand().equals("medium")){
                instance.selectedSize = "MEDIUM";
            }
            else if(e.getActionCommand().equals("large")){
                instance.selectedSize = "LARGE";
            }
            else if(e.getActionCommand().equals("elarge")){
                instance.selectedSize = "EXTRALARGE";
            }
        }
    }

    private int id;
    private String selectedSize;

    private JLabel lbl_name, lbl_asthe, lbl_iwant, lbl_sothat;
    private JTextField tf_name, tf_asthe, tf_iwant, tf_sothat;

    public FeatureBuilderMindow(int id){
        super("Feature Builder");
        this.id = id;
        this.selectedSize = null;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        BoxLayout JFrameLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        this.getContentPane().setLayout(JFrameLayout);

        JPanel stringPart = new JPanel();
        GroupLayout gl = new GroupLayout(stringPart);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);
        stringPart.setLayout(gl);
        lbl_name = new JLabel("Name ");
        tf_name = new JTextField(15);
        lbl_asthe = new JLabel("As the ");
        tf_asthe = new JTextField(15);
        lbl_iwant = new JLabel("I want ");
        tf_iwant = new JTextField(50);
        lbl_sothat = new JLabel("So that ");
        tf_sothat = new JTextField(50);
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(lbl_name)
                                .addComponent(lbl_asthe)
                                .addComponent(lbl_iwant)
                                .addComponent(lbl_sothat)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(tf_name)
                                .addComponent(tf_asthe)
                                .addComponent(tf_iwant)
                                .addComponent(tf_sothat)
                )
        );
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_name)
                                .addComponent(tf_name)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_asthe)
                                .addComponent(tf_asthe)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_iwant)
                                .addComponent(tf_iwant)
                )
                .addGroup(gl.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_sothat)
                                .addComponent(tf_sothat)
                )
        );
        
        JPanel sizePart = new JPanel();
        GroupLayout sizePartLayout = new GroupLayout(sizePart);
        sizePartLayout.setAutoCreateGaps(true);
        sizePartLayout.setAutoCreateContainerGaps(true);
        sizePart.setLayout(sizePartLayout);
        JLabel lbl_size = new JLabel("Size");
        ButtonGroup sizeGroup = new ButtonGroup();
        JRadioButton rbutt_small = new JRadioButton("SMALL");
        JRadioButton rbutt_med = new JRadioButton("MEDIUM");
        JRadioButton rbutt_large = new JRadioButton("LARGE");
        JRadioButton rbutt_elarge = new JRadioButton("EXTRA LARGE");
        rbutt_small.setActionCommand("small");
        rbutt_med.setActionCommand("medium");
        rbutt_large.setActionCommand("large");
        rbutt_elarge.setActionCommand("elarge");
        rbutt_small.addActionListener(listener);
        rbutt_med.addActionListener(listener);
        rbutt_large.addActionListener(listener);
        rbutt_elarge.addActionListener(listener);
        sizeGroup.add(rbutt_small);
        sizeGroup.add(rbutt_med);
        sizeGroup.add(rbutt_large);
        sizeGroup.add(rbutt_elarge);
        sizePartLayout.setHorizontalGroup(
            sizePartLayout.createSequentialGroup()
                            .addGroup(sizePartLayout.createParallelGroup()
                                .addComponent(lbl_size)
                                .addComponent(rbutt_small)
                            )
                            .addComponent(rbutt_med)
                            .addComponent(rbutt_large)
                            .addComponent(rbutt_elarge)
        );
        sizePartLayout.setVerticalGroup(
            sizePartLayout.createSequentialGroup()
                            .addComponent(lbl_size)
                            .addGroup(sizePartLayout.createParallelGroup()
                                .addComponent(rbutt_small)
                                .addComponent(rbutt_med)
                                .addComponent(rbutt_large)
                                .addComponent(rbutt_elarge)
                            )
        );

        JButton butt_create = new JButton("Create");
        butt_create.setActionCommand("create");
        butt_create.addActionListener(listener);
        this.getContentPane().add(stringPart);
        this.getContentPane().add(sizePart);
        this.getContentPane().add(butt_create);
        this.pack();
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent w){
                Flags.setState(Flags.IDLE);
            }
        });
        this.setVisible(true);
    }
}