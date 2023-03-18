import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UseCaseComponent extends MovableComponents {
    private Config config = Config.getInstance();
    private Color borderColor;
    private Color backgroundColor;
    private Ellipse2D oval;
    private JLabel nameLabel;

    public UseCaseComponent(String name, int backgroundColor, int borderColor) {
        init(name, backgroundColor, borderColor);
    }

    public UseCaseComponent(String name) {
        int backgroundColor = Integer.parseInt(this.config.getProperty("bo.bgColor"), 16);
        int borderColor = Integer.parseInt(this.config.getProperty("bo.bdColor"), 16);
        init(name, backgroundColor, borderColor);
    }

    public UseCaseComponent() {
        String name = this.config.getProperty("bo.UCC.name");
        int backgroundColor = Integer.parseInt(this.config.getProperty("bo.bgColor"), 16);
        int borderColor = Integer.parseInt(this.config.getProperty("bo.bdColor"), 16);
        init(name, backgroundColor, borderColor);
    }

    public void init(String name, int backgroundColor, int borderColor) {
        this.oval = new Ellipse2D.Double();
        this.nameLabel = new JLabel(name, SwingConstants.CENTER);
        this.backgroundColor = new Color(backgroundColor);
        this.borderColor = new Color(borderColor);
        setOpaque(false);
        setPreferredSize(new Dimension(200, 150));
        add(this.nameLabel);
        return;
    }

    @Override
    public boolean contains(int x, int y) {
        // System.out.println(nameLabel.getText());
        // System.out.println(oval.contains(x, y));
        // System.out.println(x);
        // System.out.println(y);
        return oval.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        int edge = 10;
        Graphics2D g2d = (Graphics2D) g;
        // 抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(this.backgroundColor);
        oval.setFrame(edge, edge, width-edge*2, height-edge*2);
        g2d.fill(this.oval);
        g2d.setColor(this.borderColor);
        g2d.drawOval(edge, edge, width - 1 - edge*2, height - edge*2);

        // add connection port
        g2d.fillRect(width/2-edge/2, height-edge, edge, edge);
        g2d.fillRect(width/2-edge/2, 0, edge, edge);
        g2d.fillRect(width-edge, height/2-edge/2, edge, edge);
        g2d.fillRect(0, height/2-edge/2, edge, edge);
        nameLabel.setBounds(0, 0, width, height);
    }
}
