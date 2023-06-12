package component.basicObject;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;


import main.Config;


public class ClassObject extends BasicObject {
    private Rectangle rectangle;

    public ClassObject() {
        super(Config.getProperty("bo.CC.name"));
        this.rectangle = new Rectangle();
        this.width =Config.getIntProperty("bo.CC.width");
        this.height = Config.getIntProperty("bo.CC.height");
        this.setSize(this.width, this.height);
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(this.width, this.height);
        Graphics2D g2d = (Graphics2D) g;
        int rectwidth = width - portSize * 2;
        int rectheight = height - portSize * 2;
        // 抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        // 畫矩形
        rectangle.setFrame(portSize, portSize, rectwidth, rectheight);
        g2d.fill(rectangle);
        g2d.setColor(borderColor);
        g2d.drawRect(portSize, portSize, rectwidth - 1, rectheight - 1);
        // first line
        g2d.drawLine(portSize, rectheight / 3 + portSize, width - portSize - 1,
                rectheight / 3 + portSize);
        // second line
        g2d.drawLine(portSize, (rectheight / 3)*2 + portSize, width - portSize - 1,
                (rectheight / 3)*2 + portSize);

        // 取得字串的長度
        int textWidth = g2d.getFontMetrics().stringWidth(this.getName());
        int textHeight = g2d.getFontMetrics().getHeight();

        // 計算置中的位置
        int x = (width - textWidth) / 2;
        int y = (rectheight / 3 - textHeight)/2+portSize+textHeight-g2d.getFontMetrics().getDescent();
        g2d.drawString(this.getName(), x, y);
    }
}
