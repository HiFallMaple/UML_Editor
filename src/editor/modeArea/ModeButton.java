package editor.modeArea;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;

import main.Config;
import mode.ModeManager;

public class ModeButton extends JButton {
    private int mode;
    private Color bgColor; // background color of button
    private Color selectedBgColor; // background color of selected button
    private Color hoveredBgColor; // background color of selected button
    private Color pressedBgColor;
    private int size;
    private boolean isSelected = false;

    public ModeButton(int i, ImageIcon icon, ActionListener actionListener, MouseListener mouseListener) {
        super();

        this.bgColor = new Color(Config.getHexIntProperty("mode.bt.bgColor"));
        this.selectedBgColor = new Color(Config.getHexIntProperty("mode.bt.selected.bgColor"));
        this.hoveredBgColor = new Color(Config.getHexIntProperty("mode.bt.hovered.bgColor"));
        this.pressedBgColor = new Color(Config.getHexIntProperty("mode.bt.pressed.bgColor"));
        this.size = Config.getHexIntProperty("mode.bt.size");

        this.setOpaque(false);
        this.addActionListener(actionListener);
        this.addMouseListener(mouseListener);
        this.setFocusPainted(false);
        this.setIcon(icon);
        this.setBackground(this.bgColor);
        this.setPreferredSize(new Dimension(size, size));
        this.setMinimumSize(new Dimension(size, size));
        this.setMaximumSize(new Dimension(size, size));
        this.setToolTipText(ModeManager.getModeName(i));
        this.setUI(new BasicButtonUI() {// 設定自訂的 ButtonUI
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                g.setColor(pressedBgColor);// 設定按下時的顏色
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        });

        this.mode = i;
    }

    public void hover() {
        setBackground(hoveredBgColor);
    }

    public void unhover() {
        if (isSelected) {
            select();
        } else {
            cleanColor();
        }
    }

    public void select() {
        setBackground(this.selectedBgColor);
        isSelected = true;
    }

    public void unselect() {
        cleanColor();
        isSelected = false;
    }

    private void cleanColor() {
        setBackground(this.bgColor);
    }

    public void setMode() {
        ModeManager.setMode(this.mode);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, 30, 30);
        g2.setColor(getForeground());
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, width - 1, height - 1, 30, 30);
        g2.dispose();
    }
}