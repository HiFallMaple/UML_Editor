package editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import mode.Mode;

public class CanvasMouseListener extends MouseAdapter {
    private static CanvasMouseListener instance;
    private Mode mode;

    private CanvasMouseListener(){
    }

    public static synchronized CanvasMouseListener getInstance() {
        if (instance == null) {
            instance = new CanvasMouseListener();
        }
        return instance;
    }

    public void setMode(Mode mode){
        this.mode = mode;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mode.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       mode.mouseReleased(e);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
       mode.mouseClicked(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mode.mouseDragged(e);
    }

}
