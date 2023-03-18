import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PaddingPanel extends JPanel {
    protected JPanel box;

    public PaddingPanel(int top, int left, int bottom, int right) {
        box = new JPanel();
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right)); // 添加10像素的Margin
        this.add(box, BorderLayout.CENTER);
    }
}
