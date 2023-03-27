package component;

import javax.swing.JPanel;

public class InteractiveComponent extends JPanel {
    protected boolean selected = false;
    protected JPanel canvas;
    protected int width;
    protected int height;

    public InteractiveComponent(JPanel canvas) {
        this.canvas = canvas;
    }


    public void select() {
        selected = true;
        revalidate();
        repaint();
    }

    public void unselect() {
        selected = false;
        revalidate();
        repaint();
    }

    public void toggleSelect() {
        selected = !selected;
        revalidate();
        repaint();
    }

    public boolean isSelected(){
        return selected;
    }
}
