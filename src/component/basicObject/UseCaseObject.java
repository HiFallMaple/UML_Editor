package component.basicObject;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import main.Config;

public class UseCaseObject extends BasicObject {

    private Ellipse2D oval;

    public UseCaseObject(String name) {
        super(name);
        init();
    }

    public UseCaseObject() {
        super(Config.getProperty("bo.UCC.name"));
        init();
    }


    public void init() {
        this.oval = new Ellipse2D.Double();
        this.setName(this.getName());
        this.width = Config.getIntProperty("bo.UCC.width");
        this.height = Config.getIntProperty("bo.UCC.height");
        setLayout(new BorderLayout());
        setSize(this.width, this.height);
        return;
    }
    
    @Override
    public boolean contains(int x, int y) {
        return oval.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(this.width, this.height);
        Graphics2D g2d = (Graphics2D) g;
        int ovalwidth = width - portSize*2;
        int ovalheight = height - portSize*2;
        // 抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        // 畫橢圓
        oval.setFrame(portSize, portSize, ovalwidth, ovalheight);
        g2d.fill(oval);
        g2d.setColor(borderColor);
        g2d.drawOval(portSize, portSize, ovalwidth - 1, ovalheight-1);
        // 取得字串的長度
        int textWidth = g2d.getFontMetrics().stringWidth(this.getName());
        int textHeight = g2d.getFontMetrics().getHeight();
        // 計算置中的位置
        int x = (width - textWidth) / 2;
        int y = (height - textHeight)/2 + textHeight - g2d.getFontMetrics().getDescent();
        g2d.drawString(this.getName(), x, y);
    }
}
