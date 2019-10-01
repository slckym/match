import javax.swing.*;

public class Calender extends JDialog {
    private JPanel contentPane;
    private JTextField textField2;
    private JTextField textField1;
    private JTable table1;
    private JButton buttonOK;

    public Calender() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Calender dialog = new Calender();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
