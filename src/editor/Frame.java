package editor;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.Border;

import editor.canvasArea.Canvas;
import editor.modeArea.ModePanel;
import main.Config;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

public class Frame extends JFrame {
    private static Frame instance;
    private static String title = "UML Editor";

    private Frame() {
        super(title);
        // Create a new JMenuBar
        MenuBar menuBar = new MenuBar();
        this.setJMenuBar(menuBar);

        Canvas canvas = Canvas.getInstance();
        JPanel controlArea = new ModePanel(Config.getIntProperty("area.padding"));
        JPanel root = new JPanel();

        this.setContentPane(root);
        root.setLayout(new BorderLayout());
        root.add(canvas, BorderLayout.CENTER);
        root.add(controlArea, BorderLayout.WEST);

        // 設定文本提示的 UI
        Insets padding = new Insets(7, 7, 7, 7);
        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(padding.top, padding.left, padding.bottom, padding.right));
        UIManager.put("ToolTip.border", border);
        UIManager.put("ToolTip.background", Color.WHITE);
        ToolTipManager.sharedInstance().setEnabled(true);
        // 預創建一個文本提示 UI，以避免第一次渲染延遲
        ToolTipManager.sharedInstance().setInitialDelay(0);
        JToolTip tmp = new JToolTip();
    }

    public static synchronized Frame getInstance() {
        if (instance == null) {
            instance = new Frame();
        }
        return instance;
    }

}
