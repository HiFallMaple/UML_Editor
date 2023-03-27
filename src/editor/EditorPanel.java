package editor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import component.AssociationLine;
import component.BaseObject;
import component.ClassComponent;
import component.CompositeComponent;
import component.CompositionLine;
import component.ConnectionLine;
import component.GeneralizationLine;
import component.InteractiveComponent;
import component.UseCaseComponent;
import main.Direction;
import main.Mode;

public class EditorPanel extends PaddingPanel {

    public ArrayList<InteractiveComponent> interactiveComponentsList;
    public ArrayList<CompositeComponent> compositeComponentsList;

    public EditorPanel(int padding) {
        super(padding, padding, padding, padding);
        addBox(new JPanel());
        interactiveComponentsList = new ArrayList<InteractiveComponent>();
        compositeComponentsList = new ArrayList<CompositeComponent>();
        // this.setLayout(null);
        box.setName("Canvas");
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.setLayout(null);
        this.add(box, BorderLayout.CENTER);
    }

    public void refresh() {
        this.box.revalidate();
        this.box.repaint();
    }

    public void addComponent(Component component) {
        this.box.add(component, 0);
    }

    public void removeComponent(Component component) {
        this.box.remove(component);
    }

    public void addInteractiveComponent(InteractiveComponent component) {
        addComponent(component);
        interactiveComponentsList.add(component);
    }

    public void removeInteractiveComponent(InteractiveComponent component) {
        removeComponent(component);
        interactiveComponentsList.remove(component);
    }

    public void addCompositeComponent(CompositeComponent component) {
        addInteractiveComponent(component);
        compositeComponentsList.add(component);
    }

    public void removeCompositeComponent(CompositeComponent component) {
        removeInteractiveComponent(component);
        compositeComponentsList.remove(component);
    }

    public void addBaseObject(int status, Point location) {
        InteractiveComponent component = null;
        if (status == Mode.CLASS) {
            component = new ClassComponent(this.box);
        } else if (status == Mode.USE_CASE) {
            component = new UseCaseComponent(this.box);
        }
        component.setLocation(location);
        addInteractiveComponent(component);
        refresh();
    }

    public void addConnectionLine(int status, BaseObject[] objects, Point pressPoint, Point releasePoint) {
        ConnectionLine line = null;
        if (status == Mode.ASSOCIATION) {
            line = new AssociationLine(pressPoint, releasePoint, Color.BLACK, 2);
        } else if (status == Mode.GENERALIZATION) {
            line = new GeneralizationLine(pressPoint, releasePoint, Color.BLACK, 2);
        } else if (status == Mode.COMPOSITION) {
            line = new CompositionLine(pressPoint, releasePoint, Color.BLACK, 2);
        } else {
            return;
        }
        addComponent(line);
        Point[] relativePoint = new Point[2];
        int[] direction = new int[2];
        for (int i = 0; i < 2; i++) {
            // 將絕對座標轉換成object的相對座標
            relativePoint[i] = SwingUtilities.convertPoint(this.box,
                    i == 0 ? pressPoint : releasePoint, objects[i]);
            // 依據相對座標計算要添加到哪個 port
            direction[i] = objects[i].getPortDirection(relativePoint[i]);
            // 將連接線的引用傳遞給 object
            objects[i].addConnectionLine(line, direction[i], i == 0 ? Direction.TAIL : Direction.HEAD);
            // 設置連接線的座標
            Point location = SwingUtilities.convertPoint(objects[i],
                    objects[i].getConnectionPortPoint(direction[i]), this.box);
            line.setPoint(location, i == 0 ? Direction.TAIL : Direction.HEAD);
        }
        refresh();
    }

}
