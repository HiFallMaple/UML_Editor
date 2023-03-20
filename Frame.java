import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        // Create a new JMenuBar
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel root = new JPanel();
        JPanel editorPanel = new EditorPanel(Config.getIntProperty("area.padding"), this);
        JPanel controlArea = new ControlPanel(Config.getIntProperty("area.padding"));

        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.add(editorPanel, BorderLayout.CENTER);
        root.add(controlArea, BorderLayout.WEST);

    }

}
