package component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

public class GeneralizationLine extends ConnectionLine {
	public GeneralizationLine(Point start, Point end, Color color, int lineWidth){
		super(start, end, color, lineWidth);
	}

	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this._setSize(this.size);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 計算箭頭的角度和長度
        double angle = Math.atan2(end.y - start.y, end.x - start.x);
        double arrowLength = 15;

        // 計算箭頭的三個點的座標
        int[] arrowX = { (int) (end.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                end.x,
                (int) (end.x - arrowLength * Math.cos(angle + Math.PI / 6)) };
        int[] arrowY = { (int) (end.y - arrowLength * Math.sin(angle - Math.PI / 6)),
                end.y,
                (int) (end.y - arrowLength * Math.sin(angle + Math.PI / 6)) };

        // 繪製箭頭線段和箭頭三角形
        g2d.drawLine(start.x, start.y, end.x, end.y);
        g2d.setColor(new Color(0xFFFFFF));
        g2d.fillPolygon(new Polygon(arrowX, arrowY, 3));
        g2d.setColor(this.color);
        g2d.drawPolygon(new Polygon(arrowX, arrowY, 3));
    }
	
}
