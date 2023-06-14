package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import editor.Canvas;

public class SelectArea extends Shape {
    private static SelectArea instance;
    private Rectangle selectArea;
    private Canvas canvas;

    private SelectArea() {
        selectArea = new Rectangle(0, 0, 0, 0);
        canvas = Canvas.getInstance();
        this.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        this.setOpaque(false);
        // this.setBackground(Color.BLUE);
    }

    public static synchronized SelectArea getInstance() {
        if (instance == null) {
            instance = new SelectArea();
        }
        return instance;
    }

    public void renewBound(Point initialPoint, Point currentPoint) {
        int x = Math.min(initialPoint.x, currentPoint.x);
        int y = Math.min(initialPoint.y, currentPoint.y);
        int width = Math.abs(currentPoint.x - initialPoint.x);
        int height = Math.abs(currentPoint.y - initialPoint.y);
        selectArea.setBounds(x, y, width, height);
    }

    @Override
    public Shape getObjectAt(Point point) {
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!selectArea.isEmpty()) {
            int alpha = 85; // 33% transparent
            g.setColor(new Color(37, 148, 216, alpha));
            g.fillRect(selectArea.x, selectArea.y, selectArea.width, selectArea.height);
            g.setColor(new Color(37, 148, 216));
            g.drawRect(selectArea.x, selectArea.y, selectArea.width, selectArea.height);

        }
    }
}
