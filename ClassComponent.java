import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;


public class ClassComponent extends BaseObject {
    private String name;
    private Rectangle rectangle;

    public ClassComponent() {
        this.rectangle = new Rectangle();
        this.name = Config.getProperty("bo.CC.name");
        this.width =Config.getIntProperty("bo.CC.width");
        this.height = Config.getIntProperty("bo.CC.height");
        this.setSize(this.width, this.height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(this.width, this.height);
        Graphics2D g2d = (Graphics2D) g;
        int rectwidth = width - connectionPortSize * 2;
        int rectheight = height - connectionPortSize * 2;
        // 抗鋸齒
        g2d.setColor(backgroundColor);
        // 畫矩形
        rectangle.setFrame(connectionPortSize, connectionPortSize, rectwidth, rectheight);
        g2d.fill(rectangle);
        g2d.setColor(borderColor);
        g2d.drawRect(connectionPortSize, connectionPortSize, rectwidth - 1, rectheight - 1);
        // first line
        g2d.drawLine(connectionPortSize, rectheight / 3 + connectionPortSize, width - connectionPortSize - 1,
                rectheight / 3 + connectionPortSize);
        // second line
        g2d.drawLine(connectionPortSize, (rectheight / 3)*2 + connectionPortSize, width - connectionPortSize - 1,
                (rectheight / 3)*2 + connectionPortSize);

        // 取得字串的長度
        int textWidth = g2d.getFontMetrics().stringWidth(name);

        // 計算置中的位置
        int x = (width - textWidth) / 2;
        int y = (rectheight / 3)/2+connectionPortSize;
        g2d.drawString(name, x, y);
    }
}
