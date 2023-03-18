import javax.swing.JButton;

public class ControlPanel extends PaddingPanel{
    public ControlPanel(int padding){
        super(padding, padding, padding, 0);
        JButton button = new JButton("Select");
        button.addActionListener((e) -> {
            System.out.println("click");
        });
        // box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        box.add(button);
    }
}
