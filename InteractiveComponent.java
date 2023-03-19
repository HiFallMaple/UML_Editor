import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InteractiveComponent extends JPanel {
    private Point originOffset;

    public InteractiveComponent() {
        addMouseMotionListener(new MoveListener());
        addMouseListener(new PressListener());
        // _addConnectableListener();
    }

    private class PressListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (Mode.getStatus() == Mode.SELECT) {
                BaseObject source = (BaseObject) e.getSource();
                source.toggleSelect();
            } 
            else if (_passEventToTopParentAboveCanvas(e)) {
                return;
            }
            // 到此時本身已為頂層物件，即可取得canvas，並傳遞事件
            passEventToCanvas(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (_passEventToTopParentAboveCanvas(e)) {
                return;
            }
            if (Mode.getStatus() == Mode.SELECT) {
                // 若狀態為 SELECT，則當前行為應為拖動物件或點擊物件，都不需要傳遞給canvas
                originOffset = new Point(e.getX(), e.getY());
            } else {
                // 到此時本身已為頂層物件，即可取得canvas，並傳遞事件
                passEventToCanvas(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 若狀態是 SELECT ，不需要針對 Released 做處理
            if (Mode.getStatus() == Mode.SELECT) {
                return;
            }
            if (_passEventToTopParentAboveCanvas(e)) {
                return;
            }
            // 到此時本身已為頂層物件，即可取得canvas，並傳遞事件
            passEventToCanvas(e);
        }
    };

    private class MoveListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (notOnMode()) {
                return;
            }
            if (_passEventToTopParentAboveCanvas(e)) {
                return;
            }
            int dx = e.getX() - originOffset.x;
            int dy = e.getY() - originOffset.y;
            setLocation(getX() + dx, getY() + dy);
        }

        private boolean notOnMode() {
            return Mode.getStatus() != Mode.SELECT;
        }
    }

    private void passEventToCanvas(MouseEvent e) {
        Component canvas = getParent();
        passEventToParent(e, canvas);
    }

    private boolean isCanvas(Container container) {
        if (container.getName() == "Canvas")
            return true;
        else
            return false;
    }

    private boolean _passEventToTopParentAboveCanvas(MouseEvent e) {
        /**
         * 如果目前組件不是canvas下第一階的層級，往上傳遞事件並回傳true讓函數跳出
         * 如果是的話什麼也不做
         */
        Component source = (Component) e.getSource();
        Container parent = source.getParent();
        if (parent == null || isCanvas(parent)) {
            return false;
        }
        while (parent != null && !isCanvas(parent)) {
            source = parent;
            parent = source.getParent();
        }
        // parent = source;
        passEventToParent(e, source);
        return true;
    }

    // private boolean _passEventToBaseObject(MouseEvent e) {
    // /**
    // * 如果目前組件不是canvas下第一階的層級，往上傳遞事件並回傳true讓函數跳出
    // * 如果是的話什麼也不做
    // */
    // Component source = (Component) e.getSource();
    // Container parent = source.getParent();
    // if (parent == null || isCanvas(parent)) {
    // return false;
    // }
    // while (parent != null && !isCanvas(parent)) {
    // source = parent;
    // parent = source.getParent();
    // }
    // // parent = source;
    // passEventToParent(e, source);
    // return true;
    // }

    private void passEventToParent(MouseEvent e, Component parent) {
        Component source = (Component) e.getSource();
        // MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e,
        // source.getParent());
        MouseEvent parentEvent = SwingUtilities.convertMouseEvent(source, e, parent);
        parent.dispatchEvent(parentEvent);
    }

}
