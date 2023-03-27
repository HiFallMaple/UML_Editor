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
import main.Mode;

public class ControlPanel extends PaddingPanel {
    private JButton[] buttons;
    private Color buttonBg; // background color of button
    private Color buttonSelectedBg; // background color of selected button
    private int buttonSize;
    private int buttonIconSize;

    public ControlPanel(int padding) {
        super(padding, padding, padding, 0);
        addBox(new JPanel());
        this.buttonBg = new Color(Config.getHexIntProperty("cp.bt.bgColor"));
        this.buttonSelectedBg = new Color(Config.getHexIntProperty("cp.bt.selected.bgColor"));
        this.buttonSize = Config.getHexIntProperty("cp.bt.size");
        this.buttonIconSize = Config.getHexIntProperty("cp.bt.icon.size");
        this.box.setLayout(new BoxLayout(this.box, BoxLayout.Y_AXIS));
        addButton();

    }

    private void addButton() {
        buttons = new JButton[Mode.modeStr.length];
        for (int i = 0; i < Mode.modeStr.length; i++) {
            JButton button = new RoundedCornerButton();
            // button.addActionListener((ActionListener) new ButtonListener(i));
            button.addActionListener(new ModeButtonListener(i));
            ImageIcon icon = getIcon(Mode.modeStr[i]);
            button.setFocusPainted(false);
            button.setIcon(icon);
            this.box.add(button);
            buttons[i] = button;
            if(Mode.getStatus() == i){
                button.setBackground(this.buttonSelectedBg);
            }else{
                button.setBackground(this.buttonBg);
            }
            button.setPreferredSize(new Dimension(buttonSize, buttonSize));
            button.setMinimumSize(new Dimension(buttonSize, buttonSize));
            button.setMaximumSize(new Dimension(buttonSize, buttonSize));
            this.box.add(Box.createVerticalStrut(10)); // 添加間格
        }
    }

    private ImageIcon getIcon(String filename) {
        ImageIcon icon = new ImageIcon("src/res/" + filename + ".png");

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

    private class ModeButtonListener implements ActionListener {
        private int buttonIndex;

        public ModeButtonListener(int i) {
            buttonIndex = i;
        }

        public void actionPerformed(ActionEvent e) {
            buttons[Mode.getStatus()].setBackground(buttonBg);
            buttons[buttonIndex].setBackground(buttonSelectedBg);
            Mode.setStatus(buttonIndex);
        }

    }

    public class RoundedCornerButton extends JButton {

        public RoundedCornerButton() {
            super();
            setOpaque(false);
            setFocusPainted(false);
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
