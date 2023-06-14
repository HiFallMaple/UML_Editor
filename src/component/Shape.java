package component;

import java.awt.Point;

import javax.swing.JPanel;

import component.line.Line;
import editor.Canvas;
import main.Direction;

public abstract class Shape extends JPanel {
    protected Canvas canvas;
    protected boolean selected;
    protected int width;
    protected int height;

    public Shape() {
        canvas = Canvas.getInstance();
        selected = false;
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
        if (isSelected()) {
            unselect();
        } else {
            select();
        }
    }

    public boolean isSelected() {
        return selected;
    }

    /**
     * 取得此物件內，該座標的物件
     * 
     * @param point 座標點
     * @return 若本物件為 BasicObject，回傳 <b>this</b>；若該座標點沒有 Object，回傳 <b>null</b>
     */
    public abstract Shape getObjectAt(Point point);

    /**
     * <p>
     * Basic object 與 composite object 的特異化
     * </p>
     * 將與此物件相關的 Line ，移至最上層
     */
    public void moveLineToTop() {
    }

    /**
     * <p>
     * Composite object 的特異化
     * </p>
     * 解除此 Object 的 group狀態
     */
    public void ungroup() {
    }

    /**
     * <p>
     * Basic object 的特異化
     * </p>
     * 取得 direction 方向上的 port 的座標(與 canvas 的相對座標)
     * 
     * @param direction port 的方向，e.g. Direction.UP
     * @return Point
     */
    public Point getPortLocation(int direction) {
        return null;
    }

    /**
     * <p>
     * Basic object 的特異化
     * </p>
     * 給定在 canvas 中的相對座標，回傳應該連接到哪個方位的連接點
     * 
     * @param point
     */
    public int getPortDirection(Point point) {
        return Direction.OUTSIDE;
    }

    /**
     * <p>
     * Basic object 的特異化
     * </p>
     * 將 line 的引用添加至此物件
     * 
     * @param line
     * @param direction     port 的方向
     * @param lineDirection line 接入此物件是 head 還是 tail
     */
    public void addLine(Line line, int direction, boolean lineDirection) {
    }

    /**
     * <p>
     * Basic object 的特異化
     * </p>
     * 將 line 的從自身的引用中刪除
     * 
     * @param line
     */
    public void removeLine(Line line) {
    }

    /**
     * 將自身加入 canvas
     */
    public void addToCanvas() {
        canvas.add(this);
    }

    /**
     * 將自身從 canvas 刪除
     */
    public void removeFromCanvas() {
        canvas.remove(this);
    }

    /**
     * <p>
     * Select object 的特異化
     * </p>
     * 透過給定現在座標，更新 SelectObject 的大小與位置
     */
    public void renewBound(Point initialPoint, Point currentPoint){
    }

    /**
     * 跳出彈窗更改名字
     */
    public void changeName(){
    }
}
