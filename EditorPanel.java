import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class EditorPanel extends PaddingPanel {

    public EditorPanel(int padding) {
        super(padding, padding, padding, padding);
        // this.setLayout(null);

        box.setName("box");
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.setLayout(null);
        // this.add(box, BorderLayout.CENTER);

        // JPanel gp = new BaseObject();
        // gp.setBounds(200, 100, 300, 300);
        // gp.setLayout(null);
        // JPanel useCaseComponent1 = new UseCaseComponent("1");
        // useCaseComponent1.setBounds(0, 0, 150, 100);
        // gp.add(useCaseComponent1);
        // box.add(gp);

        // JPanel classComponent = new ClassComponent();
        // classComponent.setBounds(0, 100, 100, 100);
        // gp.add(classComponent);

        // JPanel useCaseComponent2 = new UseCaseComponent("2");
        // useCaseComponent2.setBounds(150, 0, 150, 100);
        // gp.add(useCaseComponent2);

        JPanel useCaseComponent3 = new UseCaseComponent("3");
        useCaseComponent3.setBounds(200, 400, 150, 100);
        box.add(useCaseComponent3);

        // Point start = new Point(0, 0);
        // Point end = new Point(200, 100);
        // Color color = Color.BLACK;
        // int width = 3;
        // JPanel arrow = new ArrowComponent(start, end, color, width);
        // // JPanel arrow = new JPanel();
        // // arrow.setLocation(10, 10);
        // JPanel bo = new BaseObject();
        // bo.setLayout(new BorderLayout());
        // bo.add(arrow, BorderLayout.CENTER);
        // arrow.setOpaque(true);
        // arrow.setBackground(Color.BLUE);

        // box.add(bo);

    }
}
