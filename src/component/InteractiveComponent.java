package component;

import javax.swing.JPanel;

import main.BroadcastListener;
import main.BroadcastManager;


public class InteractiveComponent extends JPanel {
    protected boolean selected = false;
    protected JPanel canvas;
    protected int width;
    protected int height;

    public InteractiveComponent(JPanel canvas) {
        this.canvas = canvas;
        BroadcastManager.subListener(new UnselectListener());

    }

    public void select() {
        selected = true;
        System.out.println("Select");
        System.out.println(selected);
        revalidate();
        repaint();
    }

    public void unselect() {
        selected = false;
        System.out.println("Unselect");
        revalidate();
        repaint();
    }

    public void toggleSelect() {
        if (isSelected()) {
            unselect();
        } else {
            select();
        }

        revalidate();
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    private class UnselectListener implements BroadcastListener {

        @Override
        public void handle(String eventName, String message) {
            if (eventName == "unselect") {
                unselect();
            }
        }
    }
}
