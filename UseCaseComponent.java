import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UseCaseComponent extends BaseObject {

    private Ellipse2D oval;
    private JLabel nameLabel;

    public UseCaseComponent(String name) {
        init(name);
    }

    public UseCaseComponent() {
        String name = Config.getProperty("bo.UCC.name");
        init(name);
    }

    public void init(String name) {
        this.oval = new Ellipse2D.Double();
        this.setName(name);
        this.nameLabel = new JLabel(name, SwingConstants.CENTER);
        this.width = Config.getIntProperty("bo.UCC.width");
        this.height = Config.getIntProperty("bo.UCC.height");
        setLayout(new BorderLayout());
        setSize(this.width, this.height);
        add(this.nameLabel);
        return;
    }
    
    @Override
    public boolean contains(int x, int y) {
        return oval.contains(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(this.width, this.height);
        Graphics2D g2d = (Graphics2D) g;
        int ovalwidth = width - connectionPortSize*2;
        int ovalheight = height - connectionPortSize*2;
        // 抗鋸齒
        g2d.setColor(backgroundColor);
        // 畫橢圓
        oval.setFrame(connectionPortSize, connectionPortSize, ovalwidth, ovalheight);
        g2d.fill(oval);
        g2d.setColor(borderColor);
        g2d.drawOval(connectionPortSize, connectionPortSize, ovalwidth - 1, ovalheight-1);
    }
}
