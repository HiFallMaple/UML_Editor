package component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import editor.Canvas;
import main.Config;

public class SelectArea extends Shape {
    private static SelectArea instance;
    private Rectangle selectArea;
    private Canvas canvas;
    private int[] argb = {
        Config.getIntProperty("sa.color.r"),
        Config.getIntProperty("sa.color.g"),
        Config.getIntProperty("sa.color.b"),
        Config.getIntProperty("sa.color.alpha"),
    };

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
            g.setColor(new Color(argb[0], argb[1], argb[2], argb[3]));
            g.fillRect(selectArea.x, selectArea.y, selectArea.width, selectArea.height);
            g.setColor(new Color(argb[0], argb[1], argb[2]));
            g.drawRect(selectArea.x, selectArea.y, selectArea.width, selectArea.height);

        }
    }
}
