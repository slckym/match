import javax.swing.*;

public class Main extends JDialog {
    private JPanel contentPane;
    private JButton btnMatchAdditions;
    private JButton btnPosibilities;
    private JButton btnCalender;
    private JButton buttonOK;

    Main() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(100, 100, 320, 300);
        setTitle("Main");
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Main dialog = new Main();
        dialog.setVisible(true);
        System.exit(0);
    }
}
