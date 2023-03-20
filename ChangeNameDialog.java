import javax.swing.*;

public class ChangeNameDialog extends JDialog {
    private JTextField textField;

    public ChangeNameDialog(JFrame parent) {
        super(parent, "", true);
        JPanel panel = new JPanel();
        // JLabel label = new JLabel("Enter some text:");
        // panel.add(label);
        textField = new JTextField(10);
        panel.add(textField);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            setVisible(false);
        });
        panel.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            textField.setText("");
            setVisible(false);
        });
        panel.add(cancelButton);
        getContentPane().add(panel);
        pack();
        setLocationRelativeTo(parent);
    }

    public String getText() {
        return textField.getText();
    }
}
