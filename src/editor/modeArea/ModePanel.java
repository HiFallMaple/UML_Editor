package editor.modeArea;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;


import editor.PaddingPanel;
import editor.canvasArea.Canvas;
import main.Config;
import mode.ModeManager;

public class ModePanel extends PaddingPanel {
    private ModeButton[] buttons;

    public ModePanel(int padding) {
        super(padding, padding, padding, 0);
        addBox(new JPanel());

        this.box.setLayout(new BoxLayout(this.box, BoxLayout.Y_AXIS));

        addButton();
        buttons[0].doClick();
    }

    private void addButton() {
        buttons = new ModeButton[ModeManager.getModeLength()];
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentMode = ModeManager.getMode();
                buttons[currentMode].unselect();
                ModeButton button = (ModeButton) e.getSource();
                button.select();
                button.setMode();
                Canvas.getInstance().unselectAll();
            }
        };

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 鼠标进入按钮时更新外观
                ModeButton button = (ModeButton) e.getSource();
                button.hover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // 鼠标离开按钮时恢复初始外观
                ModeButton button = (ModeButton) e.getSource();
                button.unhover();
            }
        };

        for (int i = 0; i < ModeManager.getModeLength(); i++) {
            ImageIcon icon = getIcon(ModeManager.getModeName(i));
            ModeButton button = new ModeButton(i, icon, actionListener, mouseListener);
            buttons[i] = button;
            this.box.add(button);
            this.box.add(Box.createVerticalStrut(10)); // 添加間格
        }
    }

    private ImageIcon getIcon(String filename) {
        ImageIcon icon = new ImageIcon("src/res/" + filename + ".png");
        int buttonIconSize = Config.getHexIntProperty("mode.bt.icon.size");

        // 取得原始圖片寬度和高度
        int originalWidth = icon.getIconWidth();
        int originalHeight = icon.getIconHeight();

        // 計算縮放比例
        double widthRatio = (double) buttonIconSize / originalWidth;
        double heightRatio = (double) buttonIconSize / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        // 計算縮放後的寬度和高度
        int scaledWidth = (int) (originalWidth * ratio);
        int scaledHeight = (int) (originalHeight * ratio);

        // 縮放圖片
        Image scaledImage = icon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        icon.setImage(scaledImage);
        return icon;
    }

}
