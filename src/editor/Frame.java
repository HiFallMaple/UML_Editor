package editor;
import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Config;

import java.awt.BorderLayout;

public class Frame extends JFrame {
    public Frame(String title) {
        super(title);
        // Create a new JMenuBar
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        JPanel root = new JPanel();
        EditorPanel editorPanel = new EditorPanel(Config.getIntProperty("area.padding"));
        JPanel controlArea = new ControlPanel(Config.getIntProperty("area.padding"));
        CanvasEventController canvasEventController = new CanvasEventController(editorPanel, this);

        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.add(editorPanel, BorderLayout.CENTER);
        root.add(controlArea, BorderLayout.WEST);

    }

}