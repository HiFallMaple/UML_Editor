import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class BaseObject extends InteractiveComponent {
    // private int padding;
    protected int width = 0;
    protected int height = 0;
    protected Color backgroundColor = null;
    protected Color borderColor = null;
    protected int connectionPortSize = Config.getIntProperty("bo.connectionPortSize");
    protected JPanel box = null;
    protected ConnectionLine[] connectionLineList = null;// up left down right
    protected boolean[] lineDirectionList = null; // 各個方向的連接點接收的是箭頭的頭還是尾巴，true為頭
    protected boolean isSelect = false;

    public BaseObject() {
        setOpaque(false);
        int backgroundColor = Config.getHexIntProperty("bo.bgColor");
        int borderColor = Config.getHexIntProperty("bo.bdColor");
        this.backgroundColor = new Color(backgroundColor);
        this.borderColor = new Color(borderColor);
        this.connectionLineList = new ConnectionLine[4];
        this.lineDirectionList = new boolean[4];

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(this.connectionPortSize, this.connectionPortSize,
                this.connectionPortSize, this.connectionPortSize)); // 添加10像素的Margin
    }

    public void select() {
        isSelect = true;
        revalidate();
        repaint();
    }

    public void unselect() {
        isSelect = false;
        revalidate();
        repaint();
    }

    public void toggleSelect() {
        isSelect = !isSelect;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshConnectionLine();
        int connectionPortSize = 10;
        Graphics2D g2d = (Graphics2D) g;
        // 抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelect) {
            // add connection port
            // up
            g2d.fillRect(this.width / 2 - connectionPortSize / 2, 0, connectionPortSize, connectionPortSize);
            // left
            g2d.fillRect(0, this.height / 2 - connectionPortSize / 2, connectionPortSize, connectionPortSize);
            // down
            g2d.fillRect(this.width / 2 - connectionPortSize / 2, this.height - connectionPortSize, connectionPortSize,
                    connectionPortSize);
            // right
            g2d.fillRect(this.width - connectionPortSize, this.height / 2 - connectionPortSize / 2, connectionPortSize,
                    connectionPortSize);
        }
    }

    public void addConnectionLine(ConnectionLine line, int direction, boolean lineDirection) {
        this.connectionLineList[direction] = line;
        this.lineDirectionList[direction] = lineDirection;
    }

    public Point getConnectionPortPoint(int direction) {
        Point connectionPortPoint = null;
        if (direction == Direction.UP) {// up
            connectionPortPoint = new Point(this.width / 2 - connectionPortSize / 2, 0);
        } else if (direction == Direction.LEFT) {// left
            connectionPortPoint = new Point(0, this.height / 2 - connectionPortSize / 2);
        } else if (direction == Direction.DOWN) {// down
            connectionPortPoint = new Point(this.width / 2 - connectionPortSize / 2, this.height - connectionPortSize);
        } else if (direction == Direction.RIGHT) {// right
            connectionPortPoint = new Point(this.width - connectionPortSize, this.height / 2 - connectionPortSize / 2);
        }
        return connectionPortPoint;
    }

    private void refreshConnectionLine() {
        for (int i = 0; i < 4; i++) {
            if (connectionLineList[i] != null) {
                Point point = getConnectionPortPoint(i);
                if (lineDirectionList[i]) { // true為頭
                    connectionLineList[i].setEndPoint(point);
                } else {
                    connectionLineList[i].setStartPoint(point);
                }
            }
        }
    }

}
