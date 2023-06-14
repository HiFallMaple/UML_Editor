package editor;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Config;

import java.awt.BorderLayout;

public class Frame extends JFrame {
    private static Frame instance;
    private static String title = "UML Editor";

    private Frame() {
        super(title);
        // Create a new JMenuBar
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel root = new JPanel();
        Canvas canvas = Canvas.getInstance();
        canvas.setFrame(this);
        JPanel controlArea = new ControlPanel(Config.getIntProperty("area.padding"));
        
        this.setContentPane(root);
        
        root.setLayout(new BorderLayout());
        root.add(canvas, BorderLayout.CENTER);
        root.add(controlArea, BorderLayout.WEST);
    }

    public static synchronized Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
        }
        return instance;
    }

}
