import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new Frame("UML Editor");
		frame.setSize(400, 500);// 400 width and 500 height
		frame.setLocation(400, 300); // 設定窗口左上角位置
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);// 設定是否顯示
	}
}