import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ClassComponent extends MovableComponents {
    Color borderColor = new Color(0x000000);


    public ClassComponent(){

        // 設定背景顏色為灰色
        setBackground(new Color(219, 219, 219));
        setBorder(BorderFactory.createLineBorder(borderColor, 1));

        // 設定為垂直排列的BoxLayout佈局
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 添加三個JLabel組件
        JLabel label1 = new JLabel("Label 1");
        JLabel label2 = new JLabel("Label 2");
        JLabel label3 = new JLabel("Label 3");
        

        add(label1);
        add(createSeparator());
        add(label2);
        add(createSeparator());
        add(label3);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = this.getWidth();
        int height = this.getHeight();
    }

    private JSeparator createSeparator(){
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setForeground(borderColor);
        return separator;
    }
}
