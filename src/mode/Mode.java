package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import editor.Canvas;

public abstract class Mode{
    protected Canvas canvas;
    protected Point pressPoint;
    protected Point releasePoint;
    protected Point clickPoint;
    protected boolean isDragged;

    public Mode() {
        canvas = Canvas.getInstance();
        pressPoint = null;
        releasePoint = null;
        clickPoint = null;
        isDragged = false;
    }

    public void mouseClicked(MouseEvent e) {
        clickPoint = e.getPoint();
    }

    public void mousePressed(MouseEvent e) {
        pressPoint = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        releasePoint = e.getPoint();
    }

    public void mouseDragged(MouseEvent e) {
        isDragged = true;
    }
}
