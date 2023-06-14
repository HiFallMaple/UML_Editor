package main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import editor.Frame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = Frame.getInstance();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 設定 JFrame 為最大化
		frame.setVisible(true);// 設定是否顯示
	}
}