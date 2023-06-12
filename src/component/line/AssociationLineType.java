package component.line;

import java.awt.Graphics2D;
import java.awt.Point;

public class AssociationLineType extends LineType{

    @Override
    protected void drawTail(Graphics2D g2d, Point head, Point tail) {
    }

    @Override
    protected void drawHead(Graphics2D g2d, Point head, Point tail) {
        // 計算箭頭的角度和長度
        double angle = Math.atan2(tail.y - head.y, tail.x - head.x);
        double arrowLength = 15;

        // 計算箭頭的三個點的座標
        int[] arrowX = { (int) (tail.x - arrowLength * Math.cos(angle - Math.PI / 6)),
                tail.x,
                (int) (tail.x - arrowLength * Math.cos(angle + Math.PI / 6)) };
        int[] arrowY = { (int) (tail.y - arrowLength * Math.sin(angle - Math.PI / 6)),
                tail.y,
                (int) (tail.y - arrowLength * Math.sin(angle + Math.PI / 6)) };

        // 繪製箭頭  <-
		for (int i = 0; i<2; i++){
			g2d.drawLine(arrowX[i], arrowY[i], arrowX[i+1], arrowY[i+1]);
		}
    }
}
