import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends PaddingPanel{
    public ControlPanel(int padding){
        super(padding, padding, padding, 0);
        addBox(new JPanel());
        JButton button = new JButton("Select");
        button.addActionListener((e) -> {
            System.out.println("click");
        });
        // box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.box.add(button);
    }
}
