import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Frame extends JFrame {
    private Config config = Config.getInstance();
    public Frame(String title) {
        super(title);
        // Create a new JMenuBar
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel root = new JPanel();
        JPanel editorPanel = new EditorPanel(Integer.parseInt(this.config.getProperty("area.padding")));

        JPanel controlArea = new ControlPanel(Integer.parseInt(this.config.getProperty("area.padding")));

        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.add(editorPanel, BorderLayout.CENTER);
        root.add(controlArea, BorderLayout.WEST);

        

    }

}
