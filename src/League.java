import javax.swing.*;

public class League extends JFrame {
    private JPanel contentPane;

    public League() {
        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        League dialog = new League();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
