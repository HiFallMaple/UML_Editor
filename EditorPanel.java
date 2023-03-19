import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class EditorPanel extends PaddingPanel {

    public EditorPanel(int padding) {
        super(padding, padding, padding, padding);
        addBox(new JPanel());
        // this.setLayout(null);
        box.setName("Canvas");
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.setLayout(null);
        box.addMouseListener(new CanvasMouseListener());
        this.add(box, BorderLayout.CENTER);

        JPanel gp = new CompositeComponent();
        gp.setBounds(200, 100, 500, 300);
        gp.setLayout(null);
        JPanel useCaseComponent1 = new UseCaseComponent("1");
        useCaseComponent1.setLocation(10, 10);
        gp.add(useCaseComponent1);
        box.add(gp);

        JPanel classComponent = new ClassComponent();
        classComponent.setLocation(0, 100);
        box.add(classComponent);

        JPanel useCaseComponent2 = new UseCaseComponent("2");
        useCaseComponent2.setLocation(250, 0);
        gp.add(useCaseComponent2);

        JPanel useCaseComponent3 = new UseCaseComponent("3");
        useCaseComponent3.setLocation(200, 400);
        box.add(useCaseComponent3);


        JPanel useCaseComponent4 = new UseCaseComponent("4");
        useCaseComponent4.setLocation(355, 737);
        box.add(useCaseComponent4);



        Point start = new Point(1, 1);
        Point end = new Point(200, 100);
        Color color = Color.BLACK;
        int width = 3;
        ConnectionLine arrow = new ConnectionLine(start, end, color, width);

        arrow.setStartPoint(new Point(600, 1000));
        box.add(arrow);

    }


    private class CanvasMouseListener extends MouseAdapter {
        private Point pressPoint = null;
        private Point releasePoint = null;
        private boolean isDragged = false;

        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed canvas");
            pressPoint = new Point(e.getX(), e.getY());
            System.out.println(pressPoint);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 如果拖動的話就跳出
            if (isDragged) {
                isDragged = false;
                return;
            }
            releasePoint = new Point(e.getX(), e.getY());
            System.out.println("Released canvas");
            System.out.println(releasePoint);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Point clickePoint = new Point(e.getX(), e.getY());
            System.out.println("Click canvas");
            System.out.println(clickePoint);

            if (Mode.getStatus() == Mode.CLASS || Mode.getStatus() == Mode.USE_CASE){
                JPanel component = null;
                if (Mode.getStatus() == Mode.CLASS){
                    component = new ClassComponent();
                }else if (Mode.getStatus() == Mode.USE_CASE){
                    component = new UseCaseComponent();
                }
                component.setLocation(clickePoint);
                EditorPanel.this.box.add(component);
                EditorPanel.this.box.revalidate();
                EditorPanel.this.box.repaint();
            }
        }
    }

}
