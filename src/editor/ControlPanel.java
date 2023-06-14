package editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import main.Config;
import mode.ModeManager;

public class ControlPanel extends PaddingPanel {
    private ModeButton[] buttons;

    public ControlPanel(int padding) {
        super(padding, padding, padding, 0);
        addBox(new JPanel());

        this.box.setLayout(new BoxLayout(this.box, BoxLayout.Y_AXIS));

        addButton();
        buttons[0].doClick();
    }

    private void addButton() {
        buttons = new ModeButton[ModeManager.getModeLength()];
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentMode = ModeManager.getMode();
                buttons[currentMode].unselect();
                ModeButton button = (ModeButton) e.getSource();
                button.select();
                button.setMode();
            }
        };

        for (int i = 0; i < ModeManager.getModeLength(); i++) {
            ImageIcon icon = getIcon(ModeManager.getModeName(i));
            ModeButton button = new ModeButton(i, icon, listener);
            buttons[i] = button;
            this.box.add(button);
            this.box.add(Box.createVerticalStrut(10)); // 添加間格
        }
    }

    private ImageIcon getIcon(String filename) {
        ImageIcon icon = new ImageIcon("src/res/" + filename + ".png");
        int buttonIconSize = Config.getHexIntProperty("cp.bt.icon.size");

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

    public class ModeButton extends JButton {
        private int mode;
        private Color bgColor; // background color of button
        private Color selectedBgColor; // background color of selected button
        private int size;

        public ModeButton(int i, ImageIcon icon, ActionListener listener) {
            super();

            this.bgColor = new Color(Config.getHexIntProperty("cp.bt.bgColor"));
            this.selectedBgColor = new Color(Config.getHexIntProperty("cp.bt.selected.bgColor"));
            this.size = Config.getHexIntProperty("cp.bt.size");

            setOpaque(false);
            addActionListener(listener);
            setFocusPainted(false);
            setIcon(icon);
            setBackground(this.bgColor);
            setPreferredSize(new Dimension(size, size));
            setMinimumSize(new Dimension(size, size));
            setMaximumSize(new Dimension(size, size));

            this.mode = i;
        }

        public void select(){
            setBackground(this.selectedBgColor);
        }

        public void unselect(){
            setBackground(this.bgColor);
        }

        public void setMode() {
            ModeManager.setMode(this.mode);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, width, height, 30, 30);
            g2.setColor(getForeground());
            super.paintComponent(g2);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, width - 1, height - 1, 30, 30);
            g2.dispose();
        }
    }

}
