package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.BroadcastListener;
import main.BroadcastManager;
import main.Config;
import main.Direction;

public class BaseObject extends InteractiveComponent {
    protected Color backgroundColor = null;
    protected Color borderColor = null;
    protected int connectionPortSize = Config.getIntProperty("bo.connectionPortSize");
    protected ConnectionLine[] connectionLineList = null;// up left down right
    protected ArrayList<ArrayList<LinePairs>> linePairsArrayList;

    public BaseObject(JPanel canvas, String name) {
        super(canvas);
        setOpaque(false);
        int backgroundColor = Config.getHexIntProperty("bo.bgColor");
        int borderColor = Config.getHexIntProperty("bo.bdColor");
        this.backgroundColor = new Color(backgroundColor);
        this.borderColor = new Color(borderColor);
        this.connectionLineList = new ConnectionLine[4];
        this.linePairsArrayList = new ArrayList<ArrayList<LinePairs>>();
        this.setName(name);
        for (int i = 0; i < 4; i++) {
            this.linePairsArrayList.add(new ArrayList<LinePairs>());
            this.linePairsArrayList.set(i, new ArrayList<LinePairs>());
        }
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(this.connectionPortSize, this.connectionPortSize,
                this.connectionPortSize, this.connectionPortSize)); // 添加10像素的Margin

        BroadcastManager.subListener(new UnselectListener());
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        repaint();
    }

    private class UnselectListener implements BroadcastListener {

        @Override
        public void handle(String eventName, String message) {
            if (eventName == "unselect") {
                unselect();
            }
        }
    }

    private class LinePairs {
        public ConnectionLine line;
        public boolean lineDirection; // 箭頭的方向

        public LinePairs(ConnectionLine line, boolean lineDirection) {
            this.line = line;
            this.lineDirection = lineDirection;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshConnectionLine();
        int connectionPortSize = 10;
        Graphics2D g2d = (Graphics2D) g;
        // 抗鋸齒
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (selected) {
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

    public int getPortDirection(Point point) {
        /**
         * 給定與此物件的相對座標，回傳應該連接到哪個方位的連接點
         */
        Point port = new Point((-1 * ((float) height / width) * point.x + height - point.y) > 0 ? 1 : 0 // 1:左上 0:右下
                , ((float) height / width * point.x - point.y) > 0 ? 1 : 0);// 1:右上 0:左下

        int result = port.x * 2 + port.y;

        switch (result) {
            case 0:
                return Direction.DOWN;
            case 1:
                return Direction.RIGHT;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.UP;
        }

        return -1;
    }

    public void addConnectionLine(ConnectionLine line, int direction, boolean lineDirection) {
        this.connectionLineList[direction] = line;
        this.linePairsArrayList.get(direction).add(new LinePairs(line, lineDirection));
    }

    public Point getConnectionPortPoint(int direction) {
        Point connectionPortPoint = null;
        if (direction == Direction.UP) {// up
            connectionPortPoint = new Point(this.width / 2, connectionPortSize);
        } else if (direction == Direction.LEFT) {// left
            connectionPortPoint = new Point(connectionPortSize, this.height / 2);
        } else if (direction == Direction.DOWN) {// down
            connectionPortPoint = new Point(this.width / 2, this.height - connectionPortSize);
        } else if (direction == Direction.RIGHT) {// right
            connectionPortPoint = new Point(this.width - connectionPortSize, this.height / 2);
        }
        return connectionPortPoint;
    }

    private void refreshConnectionLine() {
        for (int i = 0; i < 4; i++) {
            for (LinePairs pair : linePairsArrayList.get(i)) {
                Point point = SwingUtilities.convertPoint(this, getConnectionPortPoint(i), this.canvas);
                if (pair.lineDirection == Direction.HEAD) {
                    pair.line.setEndPoint(point);
                } else {
                    pair.line.setStartPoint(point);
                }
            }

        }
    }

    public void moveLineToTop() {
        for (int i = 0; i < 4; i++) {
            for (LinePairs pair : linePairsArrayList.get(i)) {
                canvas.setComponentZOrder(pair.line, 0);
            }
        }
    }

}
