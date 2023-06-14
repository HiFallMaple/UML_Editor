package mode.objectMode;

import java.awt.event.MouseEvent;

import component.Shape;
import mode.Mode;


public abstract class NewObjectMode extends Mode {
    
    public NewObjectMode(){
        super();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        Shape object = newObject();
        object.setLocation(clickPoint);
        object.addToCanvas();;
    }

    protected abstract Shape newObject();
}
