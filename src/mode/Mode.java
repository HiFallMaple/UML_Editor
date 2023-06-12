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

    private static int modeIndex = 0;
    public final static int SELECT = 0;
    public final static int ASSOCIATION = 1;
    public final static int GENERALIZATION = 2;
    public final static int COMPOSITION = 3;
    public final static int CLASS = 4;
    public final static int USE_CASE = 5;
    public final static String[] modeStr = {"SELECT", "ASSOCIATION", "GENERALIZATION", "COMPOSITION", "CLASS", "USE_CASE"};
    public final static Mode[] modeInstances = {
        new SelectMode(),
        new AssociationLineMode(),
        new GeneralizationLineMode(),
        new CompositionLineMode(),
        new ClassMode(),
        new UseCaseMode()
    };

    public static int getStatus(){
        return modeIndex;
    }
    
    public static String getStatusStr(){
        return modeStr[modeIndex];
    }
    
    public static void setStatus(int newMode){
        modeIndex = newMode;
    }

    public static Mode getModeInstances(int modeIndex){
        return modeInstances[modeIndex];
    }

    public Mode() {
        canvas = Canvas.getInstance();
        pressPoint = null;
        releasePoint = null;
        clickPoint = null;
        isDragged = false;
    }

    public void mouseClicked(MouseEvent e) {
        clickPoint = new Point(e.getX(), e.getY());
    }

    public void mousePressed(MouseEvent e) {
        pressPoint = new Point(e.getX(), e.getY());
    }

    public void mouseReleased(MouseEvent e) {
        releasePoint = new Point(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        isDragged = true;
    }
}
