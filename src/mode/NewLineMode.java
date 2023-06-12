package mode;

import java.awt.Point;
import java.awt.event.MouseEvent;

import component.Shape;
import component.line.Line;

public abstract class NewLineMode extends Mode {

    public NewLineMode() {
        super();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        // System.out.println("Released canvas");
        // System.out.println(releasePoint);
        Shape[] objects = {
                canvas.getBasicObjectAt(pressPoint), // 尾巴
                canvas.getBasicObjectAt(releasePoint), // 頭
        };

        // 如果 press 與 release 都在 basic object 上，並且為不同個物件
        if (objects[0] != null && objects[1] != null && objects[0] != objects[1]) {
            Line line = newLine();
            Point[] points = line.bindLineToObject(objects, pressPoint, releasePoint);
            line.setSizeLocation(points[0], points[1]);
            canvas.addObject(line);
        }
    }


    protected abstract Line newLine();
}
