import javax.swing.*;

public class Main extends JDialog {
    private JPanel contentPane;
    private JButton btnMatchAdditions;
    private JButton btnPosibilities;
    private JButton btnCalender;

    private Main() {
        initialize();
        btnCalender.addActionListener(actionEvent -> {
            setVisible(false);
            Calender.visible(true);
        });
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        setBounds(100, 100, 320, 300);
        setTitle("Main");
        Helper.centreWindow(this);
    }

    public static void main(String[] args) {
        Main.visible(true);
    }

    @SuppressWarnings("SameParameterValue")
    static void visible(boolean b) {
        Main main = new Main();
        main.setVisible(b);
        System.exit(0);
    }
}
