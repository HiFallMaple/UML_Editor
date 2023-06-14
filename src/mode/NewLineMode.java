package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import component.Shape;
import component.line.Line;
import main.Direction;

public abstract class NewLineMode extends Mode {
    private Line line = null;
    private Shape pressObject = null;
    private int pressPort;

    public NewLineMode() {
        super();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (line == null) {
            pressObject = canvas.getBasicObjectAt(pressPoint);
            if (pressObject != null) {
                line = newLine();
                pressPort = pressObject.getPortDirection(pressPoint);
                Point pressPortPoint = pressObject.getPortLocation(pressPort);
                line.addToCanvas();
                line.setSizeLocation(pressPortPoint, new Point(e.getX(), e.getY()));
            }
        } else {
            Point point = new Point(e.getX(), e.getY());
            Shape object = canvas.getBasicObjectAt(point);
            if (object != null && object!=pressObject) {
                int port = object.getPortDirection(point);
                line.setHeadPoint(object.getPortLocation(port));
            } else {
                line.setHeadPoint(point);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        boolean removeline = false;
        if (isDragged && line != null && pressObject != null) {
            Shape releaseObject = canvas.getBasicObjectAt(releasePoint);

            // 如果 press 與 release 都在 basic object 上，並且為不同個物件
            if (releaseObject != null && pressObject != releaseObject) {
                int releasePort = releaseObject.getPortDirection(releasePoint);
                Point releasePortPoint = releaseObject.getPortLocation(releasePort);

                line.bindLineToObject(pressObject, pressPort, Direction.TAIL);
                line.bindLineToObject(releaseObject, releasePort, Direction.HEAD);

                line.setHeadPoint(releasePortPoint);
            } else {
                removeline = true;
            }
        } else {
            removeline = true;
        }

        if (line != null && removeline) {
            line.removeFromCanvas();
        }
        cleanValue();
    }

    private void cleanValue() {
        isDragged = false;
        line = null;
        pressObject = null;
    }

    protected abstract Line newLine();
}
