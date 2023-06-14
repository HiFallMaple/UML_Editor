package component.basicObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;

import component.line.Line;
import editor.ChangeNameDialog;
import editor.Frame;
import component.Shape;
import main.Config;
import main.Direction;

public class BasicObject extends Shape {
    protected Color backgroundColor = null;
    protected Color borderColor = null;
    protected int portSize;
    protected ArrayList<ArrayList<LinePairs>> linePairsList;// up left down right

    public BasicObject(String name) {
        super();
        setOpaque(false);
        int backgroundColor = Config.getHexIntProperty("bo.bgColor");
        int borderColor = Config.getHexIntProperty("bo.bdColor");
        this.backgroundColor = new Color(backgroundColor);
        this.portSize = Config.getIntProperty("bo.connectionPortSize");
        this.borderColor = new Color(borderColor);
        this.linePairsList = new ArrayList<ArrayList<LinePairs>>();
        this.setName(name);
        for (int i = 0; i < 4; i++) {
            this.linePairsList.add(new ArrayList<LinePairs>());
            this.linePairsList.set(i, new ArrayList<LinePairs>());
        }
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(this.portSize, this.portSize,
                this.portSize, this.portSize)); // 添加10像素的Margin

    }

    @Override
    public void setName(String name) {
        super.setName(name);
        repaint();
    }

    /**
     * 儲存每個連結的 line
     */
    private class LinePairs {
        public Line line;
        public boolean lineDirection; // 箭頭的方向

        public LinePairs(Line line, boolean lineDirection) {
            this.line = line;
            this.lineDirection = lineDirection;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        refreshLine();
        int portSize = 10;
        // 抗鋸齒
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (selected) {
            // paint connection port
            // up
            g2d.fillRect(this.width / 2 - portSize / 2, 0, portSize, portSize);
            // left
            g2d.fillRect(0, this.height / 2 - portSize / 2, portSize, portSize);
            // down
            g2d.fillRect(this.width / 2 - portSize / 2, this.height - portSize, portSize,
                    portSize);
            // right
            g2d.fillRect(this.width - portSize, this.height / 2 - portSize / 2, portSize,
                    portSize);
        }
    }

    @Override
    public void changeName(){
        ChangeNameDialog dialog = new ChangeNameDialog(Frame.getInstance());
        dialog.setVisible(true);
        if (dialog.isOk()) {
            String text = dialog.getText();
            this.setName(text);
        }
    }

    @Override
    public int getPortDirection(Point point) {
        point = canvas.convertPointToComponent(this, point);
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

        return Direction.OUTSIDE;
    }

    @Override
    public void addLine(Line line, int direction, boolean lineDirection) {
        this.linePairsList.get(direction).add(new LinePairs(line, lineDirection));
    }

    @Override
    public void removeLine(Line line){
        for (ArrayList<LinePairs> pairsList : linePairsList) {
            Iterator<LinePairs> iterator = pairsList.iterator();
            while (iterator.hasNext()) {
                LinePairs linePairs = iterator.next();
                if (linePairs.line == line) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @Override
    public Point getPortLocation(int direction) {
        Point portLocation = null;
        if (direction == Direction.UP) {// up
            portLocation = new Point(this.width / 2, portSize);
        } else if (direction == Direction.LEFT) {// left
            portLocation = new Point(portSize, this.height / 2);
        } else if (direction == Direction.DOWN) {// down
            portLocation = new Point(this.width / 2, this.height - portSize);
        } else if (direction == Direction.RIGHT) {// right
            portLocation = new Point(this.width - portSize, this.height / 2);
        }
        return canvas.convertPointToCanvas(this, portLocation);
    }

    @Override
    public void addToCanvas(){
        super.addToCanvas();
        canvas.addToShapeList(this);
    }

    @Override
    public void removeFromCanvas(){
        super.removeFromCanvas();
        canvas.removeFromShapeList(this);
    }

    /**
     * 更新與此物件相連的所有 Line 的位置
     */
    private void refreshLine() {
        for (int i = 0; i < 4; i++) {
            for (LinePairs pair : linePairsList.get(i)) {
                Point point = getPortLocation(i);
                if (pair.lineDirection == Direction.TAIL) {
                    pair.line.setTailPoint(point);
                } else {
                    pair.line.setHeadPoint(point);
                }
            }

        }
    }

    @Override
    public void moveLineToTop() {
        for (int i = 0; i < 4; i++) {
            for (LinePairs pair : linePairsList.get(i)) {
                canvas.setComponentZOrder(pair.line, 0);
            }
        }
    }

    @Override
    public Shape getObjectAt(Point point) {
        return this;
    }

}
